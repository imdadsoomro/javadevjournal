1. "eio-prod-wal-batched-glue-db"."raw_data"
2. "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded"

Following are two separate queries with one sample record each. Can you write me query to get the differences. You can join on "eio-prod-wal-batched-glue-db"."raw_data".interactionid and "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded".subject.interaction.id

"eio-prod-wal-batched-glue-db"."raw_data" has higher number of records than "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded". I want to look for missing records in "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded".

SELECT eiouniqueid,body,originsystemclientid,originalschema,eio_shared_version,partition_0,partition_1,partition_2 FROM "eio-prod-wal-batched-glue-db"."raw_data" LIMIT 1
221502286-20017-1700395084326
{individualenterpriseid=221502286, accountnumber=null, clientid=null, patientid=null, householdid=null, memberid=null, interactionevent=interaction.viewed, interactioneventvalue=JAMES has invited you to activate a myCigna account, channel=email, interactionpoint=aws_ses, interactioneventdate=2023-11-19T12:03:30.000000-0000, communicationdirection=entityToCigna, sourcesystem=crm, opportunityid=null, opportunitypath=null, outcome=null, outcomereason=null, details={hi=null, sourcesystemid=3c7ced70-6f6a-4151-882a-9d8a1e361a46, interactioneventdescription=viewed, cignalocale=null, callbackrequested=No, childopportunityid=null, contenttemplatename=20017_mycigna_en_us, contentsource=Filenet, referencedata=null, contactinfo=cmcross802@gmail.com, entitylocale=en_us, callback=No, callbackrepeat=No, transfersuceeded=No, transfer=No, transfersecond=No, callnetpromoter=0, interactioneventerrorcode=null, interactioneventerrordescription=null, contenttemplatetype=CRMCustomerInteractions,Email, pharmacyproductcode=null, sourcesystemopportunityid=null, applicationuniqueid=null, communicationname=null, collateralid=null, sourcesystemid=3c7ced70-6f6a-4151-882a-9d8a1e361a46, attemptcount=null, callbacktimepreference=null, docid=null, messagetype=null, conversationtype=null, notificationid=464d4091-3d21-4658-937b-515911d0e6be, notificationtrackingid=0100018bd46b72ea-1edf17be-4daf-44f3-a837-fe9e2b7c01ba-000000, patientidtype=null, relatednotificationtrackingid=null, affiniumprogramid=null, versionid=null, leadkeyid=null, enterpriseidtype=null, eiorredriveversion=null, eiorredriveattempts=null, ogindicator=null, itmflag=null, agentid=null, outputtemplatename=null, digitalindicator=null, cignalocale=null, communicationname=null, contactinfo=cmcross802@gmail.com, enterpriseid=null, runid=null, segmentname=null, sourcesystemid=3c7ced70-6f6a-4151-882a-9d8a1e361a46, stepid=null, parentsourcesystemid=null, collateralname=null, segmentname=null, collateralid=null, enterpriseid=null, batchid=null, customername=null, firstname=null, lastname=null, membernumber=null, phonenumber=null, productid=null, groupid=null, lineofbusiness=null, parentsourcesystemid=null, lobbrandingcd=null, channeltypecode=null, interactiondescription=null, oppor_map_key=null, response_id=null, presentationid=null, contentsource=Filenet, contenttemplatetype=CRMCustomerInteractions,Email, stepname=null, calltoaction=null, opportunityweight=null, calltoaction=null, eml_addr=null}, interactionid=221502286-20017-1700395084326, publisher=crm, recordtype=crm_ses, datecreated=2023-11-19T12:03:30.000000-0000, status=created, sourcesystemid=null, originalinteractionschema=com.cigna.eior.interactions, originalinteractionschemaversion=v1, crm300_migration_defaults=null, correlationidentifier=464d4091-3d21-4658-937b-515911d0e6be, interactionpath=null, patientidtype=null, agentgroup=null, claimids=null, parentinteractionidentifier=null}
legacy_kafka_interaction_v1
interaction_v1
1.6.2
2023
11
47


SELECT version,jobid,status,migration,objectkey,nativeid,source,subject,originschema,migrationid,year,month,day FROM "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded" limit 1;
2023_07_28_12_57_25
f52875e6-e2b6-4e99-89e5-7d908d84e237
succeeded
eir_internal_v1_to_evir_customer_v1
okv1:8388abfd9bb1219fb75a0b85035cbc94a7a5922cae1c3dcff2e9b29d29a0a08f
eiouid:0a130720-df61-4cf3-9071-212d1cb0401c
s3://eio-prod-operations-storage-647430331721/migrate_interaction/migrations/eir_internal_v1_to_evir_customer_v1_failed_invalid/year=2023/month=07/day=23/eio-prod-operations-migration-storage-9-2023-07-23-14-59-55-3378bb6c-bfb5-31d3-9525-3459f2a7dd6f.gz
{tenantid=16777473, entityname=cigna, individualid={type=ieid, value=55128560}, interaction={type=tpv, datecreated=2021-07-09T19:03:00.000000-0000, id=4b0825eb-64c2-4668-8269-d34a3b2b52db, point=MyCigna.Wellness.Marketplace, description=null, path=null}, interactionevent={date=2021-04-01T09:04:11.000000+0000, description=AYCO|Personalized Financial Coaching, result=presented, resultdescription=null}, publisher=myCigna, sourcesystem=myCigna, channel=web, communicationdirection=memberToEntity, status=created, details=null}
interaction_v1
mig:ee0a582a-dc51-44fc-a652-531981c9403a
2023
08
15
####################
SELECT
    rd.eiouniqueid,
    rd.body,
    rd.originsystemclientid,
    rd.originalschema,
    rd.eio_shared_version,
    rd.partition_0,
    rd.partition_1,
    rd.partition_2
FROM
    "eio-prod-wal-batched-glue-db"."raw_data" rd
LEFT JOIN
    "eio-prod-evir-migration-glue-db"."migration_eir_internal_v1_to_evir_customer_v1_succeeded" mig
ON
    rd.interactionid = mig.subject.interaction.id
WHERE
    mig.subject.interaction.id IS NULL
LIMIT 1;
