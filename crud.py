from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
from pyswip import Prolog
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'crud.sqlite')
db = SQLAlchemy(app)
ma = Marshmallow(app)


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True)
    password = db.Column(db.String(120), unique=True)

    def __init__(self, username, password):
        self.username = username
        self.password = password


class UserSchema(ma.Schema):
    class Meta:
        # Fields to expose
        fields = ('username', 'password')


user_schema = UserSchema()
users_schema = UserSchema(many=True)

@app.route('/')
def index():
    return '<h1>Welcome</h1>'

# endpoint to create new user
@app.route("/user", methods=["POST"])
def add_user():
    username = request.json['username']
    password = request.json['password']

    new_user = User(username, password)

    db.session.add(new_user)
    db.session.commit()

    return user_schema.jsonify(new_user)

@app.route("/receta", methods=["POST"])
def add_receta():
    request_data = request.get_json()
    nombreIn = request_data['nombre']
    tipoIn = request_data['tipo']
    pasosIn = request_data['pasos']
    ingredienteIn = request_data['ingredientes']
    fotoIn = request_data['foto']

    prologStatement = "receta(" + nombreIn + "," + tipoIn + "," + pasosIn + "," + ingredienteIn + "," + fotoIn + ").\n"

    f = open("conocimientos.pl","a")
    f.write(prologStatement)
    f.close()

    return jsonify(nombre = nombreIn,tipo = tipoIn,pasos = pasosIn,ingredientes = ingredienteIn,foto=fotoIn)

#endpoint para el login de usuario
@app.route("/user/login", methods=["POST"])
def login():
    username = request.json['username']
    password = request.json['password']

    validacion = User("Rejected", " ")
    all_users = User.query.all()
    result = users_schema.dump(all_users)
    for user in all_users:
        if(user.username == username and user.password==password):
            validacion = User("Accepted", " ")



    return user_schema.jsonify(validacion)


# endpoint to show all users
@app.route("/user", methods=["GET"])
def get_user():
    all_users = User.query.all()
    result = users_schema.dump(all_users)
    return jsonify(result.data)


# endpoint to get user detail by id
@app.route("/user/<username>", methods=["GET"])
def user_detail(username):
    user = User.query.get(username)

    return user_schema.jsonify(user)


# endpoint to update user
@app.route("/user/<id>", methods=["PUT"])
def user_update(id):
    user = User.query.get(id)
    username = request.json['username']
    password = request.json['password']

    user.password = password
    user.username = username

    db.session.commit()
    return user_schema.jsonify(user)


# endpoint to delete user
@app.route("/user/<id>", methods=["DELETE"])
def user_delete(id):
    user = User.query.get(id)
    db.session.delete(user)
    db.session.commit()

    return user_schema.jsonify(user)
