# Fraud detection filter

This application uses a kaggle data which shows credit card fraud. The sample application uses Apache Spark to filter the data so that it can be passed through an expert rules engine to detect whether a transaction is fradulent using domain expert rules.

# Launching the app

Clone or fork the repository. 

Then you must create the resources needed to launch the application. 

oc create -f filter.yaml

You also need to make an oshinko spark cluster called "demo" - you can change the name in the below command

When the image is built then run the job. 

oc new-app --template=filter -e SPARK_OPTIONS='--packages org.postgresql:postgresql:42.1.4 --conf spark.jars.ivy=/tmp/.ivy2' -e OSHINKO_CLUSTER_NAME=demo

This should run the job and will filter out the transactions from the "transactions" table to the "results" table in the db.

login to check:

psql -U username -d finance

\d - to see tables
