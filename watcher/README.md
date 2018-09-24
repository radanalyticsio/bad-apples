# postgresql-watcher

This application is a simple HTTP host that will watch a database for new
records to appear and then display those records through a browser.

It is meant to be used in conjunction with the
[transaction_limit](https://github.com/rebeccaSimmonds19/transaction_limit)
application for demonstration and tutorial purposes.

## OpenShift quickstart

To launch the applicationo on OpenShift you must first have a PostgreSQL
database running. The watcher will expect a table named `results` with a
schema of `CREATE TABLE results (count int)`.

```
oc new-app centos/python-36-centos7~https://github.com/elmiko/postgresql-watcher \
  -e DBHOST=postgresql \
  -e DBNAME=finance \
  -e DBUSERNAME=username \
  -e DBPASSWORD=password
```

Please note that the individual values for the environment variables should
be modified to suit the details you used when creating the database.
