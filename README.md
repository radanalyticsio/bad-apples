# transaction_limit_data_loader

oc new-app --template=postgresql-persistent \
  -p POSTGRESQL_USER=username \
  -p POSTGRESQL_PASSWORD=password \
  -p POSTGRESQL_DATABASE=finance
  
oc create -f secret.yaml

oc create -f data-loader.yaml

oc new-app --template=data-loader
