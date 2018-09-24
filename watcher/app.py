import argparse
import logging
import os

import flask
from flask import json
from flask import views
import psycopg2 as psy


class RootView(views.MethodView):
    def get(self):
        return flask.render_template('index.html')


class ItemsView(views.MethodView):
    def __init__(self, args):
        self.args = args

    def get(self):
        con = psy.connect(
                host=self.args.host,
                user=self.args.username,
                password=self.args.password,
                dbname=self.args.dbname)
        cur = con.cursor()
        try:
            cur.execute('select * from results')
            res = [i[0] for i in cur.fetchall()]
        except Exception:
            res = []
        cur.close()
        con.close()
        return json.jsonify(res)


def main(args):
    app = flask.Flask(__name__)
    app.add_url_rule(
        '/', view_func=RootView.as_view('root'))
    app.add_url_rule(
        '/items', view_func=ItemsView.as_view('items', args))
    app.run(host='0.0.0.0', port=8080)


def get_arg(env, default):
    """Extract command line args, else use defaults if none given."""
    return os.getenv(env) if os.getenv(env, '') is not '' else default


def parse_args(parser):
    args = parser.parse_args()
    args.host = get_arg('DBHOST', args.host)
    args.dbname = get_arg('DBNAME', args.dbname)
    args.username = get_arg('DBUSERNAME', args.username)
    args.password = get_arg('DBPASSWORD', args.password)
    return args


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO)
    logging.info('starting postgresql-watcher')
    parser = argparse.ArgumentParser(
            description='watch a postgresql db')
    parser.add_argument(
            '--db-host',
            dest='host',
            help='hostname for the postgresql database, env variable DBHOST')
    parser.add_argument(
            '--db-name',
            dest='dbname',
            help='database name to watch, env variable DBNAME')
    parser.add_argument(
            '--username',
            help='username for the database, env variable DBUSERNAME')
    parser.add_argument(
            '--password',
            help='password for the database, env variable DBPASSWORD')
    args = parse_args(parser)
    main(args)
