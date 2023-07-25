import json

import boto3
from dateutil.parser import isoparse
from argparse import ArgumentParser, Namespace

parser = ArgumentParser(description="merges daily indices into their corresponding weekly indices")
parser.add_argument(
    "--env",
    type=str,
    choices=["destruct", "dev", "int", "pvs", "prod"],
    help="dictates which environment the index consolidation takes place.",
)

def main(config: Namespace):
    client = boto3.client("lambda", verify=False)
    response = client.invoke(
        FunctionName=f"eio-{config.env}-administer_esg_http_store-pipeline-lambda",
        InvocationType="RequestResponse",
        Payload=b'{"type": "list_indices"}',
    )

    indices = json.loads(response["Payload"].read())["result"]
    daily_indices = list(
        filter(
            lambda idx: idx["index"].startswith("customer_interactions-")
            and "w" not in idx["index"],
            indices,
        )
    )

    weekly_indices = {}
    for index in daily_indices:
        index_date = index["index"][-10:]
        (year, week, _) = isoparse(index_date).isocalendar()
        weekly_index_name = f"customer_interactions-{year}-w{week}"
        if weekly_index_name not in weekly_indices:
            weekly_indices[weekly_index_name] = []
        weekly_indices[weekly_index_name].append(index)

    for destination_index_name, source_indices in weekly_indices.items():
        source_index_names = list(map(lambda src_idx: src_idx["index"], source_indices))
        payload = {
            "type": "reindex",
            "arguments": {
                "source": source_index_names,
                "destination": destination_index_name,
                "deleteSource": False,
            },
        }

        bytes_value = bytes(json.dumps(payload), "utf-8")
        print(bytes_value)


if __name__ == "__main__":
    main(parser.parse_args())
