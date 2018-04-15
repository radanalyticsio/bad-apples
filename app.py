import uuid
import flask
from flask import json

app = flask.Flask(__name__)

item_list = []

@app.route('/')
def index():
    return flask.render_template('index.html')


@app.route('/items')
def items():
    return json.jsonify(item_list)

@app.route('/items', methods=['post'])
def add_items():
    item_list.append({'id': uuid.uuid4().hex})
    return '', 204

if __name__ == '__main__':
    print('main')
