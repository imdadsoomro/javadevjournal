import json
import os
from base64 import b64decode
from os import getenv
from uuid import uuid4

import boto3
from aws_lambda_powertools import Logger, Metrics
from aws_lambda_powertools.metrics import Metrics, MetricUnit
from evir_models.envelopes import (
    EnvelopeSource,
    EnvelopeSourceComponent,
    wrap_with_ingress_v1,
)
from evir_models.validation import get_validator_by_schema_name
from pkg_resources import get_distribution

from receive_customer_interaction_v1.models import SubmitInteractionResult

logger = Logger()
kinesis_client = boto3.client("kinesis")
stream_name = os.environ["KINESIS_STREAM_NAME"]


def respond_lambda(status_code, body) -> dict:
    return {
        "statusCode": status_code,
        "headers": {"content-type": "application/json"},
        "body": body.json(exclude_none=True),
        "isBase64Encoded": False,
    }


def publish_to_stream(request, ingest_id):
    wrapped_ingress_interaction = wrap_with_ingress_v1(
        source=EnvelopeSource(
            component=EnvelopeSourceComponent(
                name="receive-customer-interaction-v1",
                version=getenv("EIR_VERSION", "not-specified"),
            ),
            origin_schema="evir_interaction_customer_v1",
            channel="/evir/v1/interactions/customer/$ingest",
        ),
        interaction=request,
        ingest_id=ingest_id,
    )

    kinesis_client.put_record(
        StreamName=stream_name,
        Data=json.dumps(wrapped_ingress_interaction).encode(),
        PartitionKey=str(uuid4()),
    )


def create_metrics():
    metrics = Metrics()
    metrics.set_default_dimensions(
        env=getenv("ENV", "not-specified"),
    )
    return metrics


METRICS = create_metrics()

logger.info(
    "initialising receive-customer-interaction-v1",
    extra={
        "ingestStream": stream_name,
        "dependencies": {
            "evirModels": get_distribution("cigna-clinical-cis-evir-models").version
        },
    },
)


@METRICS.log_metrics
def lambda_handler(event, context):
    try:
        logger.append_keys(
            esrxRequestId=event.get("headers").get("ESRX-Request-ID", "not-provided")
        )
        body = event["body"]

        if event["isBase64Encoded"] is True:
            body = json.loads(b64decode(body.encode()).decode("utf-8"))

        logger.debug(
            "received event",
            extra={
                "event": event,
                "context": context,
                "decodedBody": body,
            },
        )
        validator = get_validator_by_schema_name("evir_interaction_customer_v1")

        if isinstance(body, dict):
            request = body
        else:
            request = json.loads(body)

        issues = validator.validate(request)
        validation_issues = list(issues)

        if len(validation_issues) > 0:
            logger.warning(
                "invalid interaction submitted",
                extra={
                    "issues": validation_issues,
                    "interaction": request,
                },
            )
            METRICS.add_metric(
                name="SubmissionRejectedCount", unit=MetricUnit.Count, value=1
            )
            return respond_lambda(
                "400", SubmitInteractionResult.from_failed_validation(validation_issues)
            )

        ingest_id = str(uuid4())
        publish_to_stream(request, ingest_id)

        METRICS.add_metric(name="SubmissionAcceptedCount", unit=MetricUnit.Count, value=1)

        return respond_lambda("200", SubmitInteractionResult.success(ingest_id))

    except Exception:  # pylint:disable=broad-except
        logger.exception(
            "unexpected exception encountered",
            extra={
                "body": event.get("body"),
                "isBase64Encoded": event.get("isBase64Encoded"),
            },
        )
        METRICS.add_metric(name="SubmissionRejectedCount", unit=MetricUnit.Count, value=1)
        return respond_lambda("500", SubmitInteractionResult.system_error())
