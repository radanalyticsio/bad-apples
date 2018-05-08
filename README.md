# Fraud detection filter

This application uses a kaggle data which shows credit card fraud. The sample application uses Apache Spark to filter the data so that it can be passed through an expert rules engine to detect whether a transaction is fradulent using domain expert rules.

# Loading the data

Go to this link

[data loader](https://github.com/rebeccaSimmonds19/transaction_limit_data_loader)

# Launching the app

Clone or fork the repository. 

Then you must create the resources needed to launch the application. 

``
oc create -f filter.yaml
``

You will then make a spark cluster to be used by the application using the oshinko project.

For instructions to use oshinko please visit [getting started](https://radanalytics.io/get-started)

The next command will then run a job which will deploy and run the Apache Spark filtering and the Drools rule. 

``
oc new-app --template=filter -e SPARK_OPTIONS='--packages org.postgresql:postgresql:42.1.4 --conf spark.jars.ivy=/tmp/.ivy2' -e OSHINKO_CLUSTER_NAME=demo
``

This will execute the job and will filter out the transactions from the "transactions" table to the "results" table in the db.

You can login to psql to check using:

``
psql -U username -d finance
``



``
\d - to see tables
``
