import os
import uuid
import flask
from flask import json
from flask import views
import psycopg2 as psy


class RootView(views.MethodView):
    def get(self):
        return flask.render_template('index.html')


class ItemsView(views.MethodView):
    def __init__(self, con):
        self.con = con

    def get(self):
        cur = self.con.cursor()
        cur.execute('select * from results')
        res = [i[0] for i in cur.fetchall()]
        cur.close()
        return json.jsonify(res)


if __name__ == '__main__':
    con = psy.connect(
            dbname=os.environ.get('DBNAME', 'finance'),
            user=os.environ.get('DBUSERNAME', 'username'),
            password=os.environ.get('DBPASSWORD', 'password'),
            host=os.environ.get('DBHOST', '127.0.0.1'))
    app = flask.Flask(__name__)
    app.add_url_rule(
        '/', view_func=RootView.as_view('root'))
    app.add_url_rule(
        '/items', view_func=ItemsView.as_view('items', con))
    app.run(port=8080)
    con.close()
