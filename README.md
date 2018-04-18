# transaction_limit
To change the limit change the hard coded calue in App.java

Create the image stream, buildconfig and template. Make sure you do this early to build the image stream as it takes a while

oc create -f filter.yaml

You also need to make an oshinko spark cluster called "demo" - you can change the name in the below command

When the image is built then run the job. 

oc new-app --template=filter -e SPARK_OPTIONS='--packages org.postgresql:postgresql:42.1.4 --conf spark.jars.ivy=/tmp/.ivy2' -e OSHINKO_CLUSTER_NAME=demo

This should run the job and will filter out the transactions from the "transactions" table to the "results" table in the db.

login to check:

psql -U username -d finance

\d - to see tables
