# Data loader

These instructions will create a postgreSQL db and then load some fraud data from kaggle into it.

(data)[https://www.kaggle.com/mlg-ulb/creditcardfraud]

Create the database

``oc new-app --template=postgresql-ephemeral 
  -p POSTGRESQL_USER=username 
  -p POSTGRESQL_PASSWORD=password 
  -p POSTGRESQL_DATABASE=finance``
  
Create the job to load the data

``oc create -f data-loader.yaml``

Then run the job to make the data

``oc new-app --template=data-loader``
