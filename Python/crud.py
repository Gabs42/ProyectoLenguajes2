from flask import Flask, request, jsonify,Response
import json
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
from pyswip import Prolog
import os

app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'crud.sqlite')
db = SQLAlchemy(app)
ma = Marshmallow(app)
test = 11

f = open("conocimientos.pl","a")
f.write("member2(X,[X|_]).\n")
f.write("member2(X,[Y|T]):-member2(X,T).\n")
f.close()
c=4

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

#endpoint para agregar una nueva receta
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

    return jsonify(nombre = prologStatement,tipo = tipoIn,pasos = pasosIn,ingredientes = ingredienteIn,foto=fotoIn)

#endpoint para ver todas las recetas
@app.route("/receta", methods=["GET"])
def getreceta():
    listaJson = []
    prolog = Prolog()
    prolog.consult("conocimientos.pl")
    for soln in prolog.query("receta(A,B,C,D,E)"):
        listaPasos = ""
        listaIngredientes = ""
        listaFotos = ""
        for paso in soln["C"]:
            listaPasos += paso.value +","
        for ingrediente in soln["D"]:
            listaIngredientes += ingrediente.value+","
        for foto in soln["E"]:
            listaFotos  += foto.value +","

        listaJson.append({"nombre":soln["A"],"tipo":soln["B"],"pasos":listaPasos,"ingredientes":listaIngredientes,"foto":listaFotos})
        #listaJson.append(jsonify(nombre=soln["A"],tipo = soln["B"],pasos = soln["C"][0].value,ingredientes = soln["D"][0].value,foto=soln["E"][0].value))

    return Response(json.dumps(listaJson),  mimetype='application/json')

@app.route("/receta/nombre", methods=["POST"])
def getrecetaNombre():
    request_data = request.get_json()
    nombreIn = request_data['nombre']
    tipoIn = request_data['tipo']
    pasosIn = request_data['pasos']
    ingredienteIn = request_data['ingredientes']
    fotoIn = request_data['foto']

    listaJson = []
    prolog = Prolog()
    prolog.consult("conocimientos.pl")
    for soln in prolog.query("receta("+nombreIn+","+"B,C,D,E)"):
        listaPasos = ""
        listaIngredientes = ""
        for paso in soln["C"]:
            listaPasos += paso.value +","
        for ingrediente in soln["D"]:
            listaIngredientes += ingrediente.value+","

        listaJson.append({"nombre":nombreIn,"tipo":soln["B"],"pasos":listaPasos,"ingredientes":listaIngredientes,"foto":soln["E"][0].value})
        #listaJson.append(jsonify(nombre=soln["A"],tipo = soln["B"],pasos = soln["C"][0].value,ingredientes = soln["D"][0].value,foto=soln["E"][0].value))

    return Response(json.dumps(listaJson),  mimetype='application/json')

@app.route("/receta/tipo", methods=["POST"])
def getrecetaTipo():
    request_data = request.get_json()
    nombreIn = request_data['nombre']
    tipoIn = request_data['tipo']
    pasosIn = request_data['pasos']
    ingredienteIn = request_data['ingredientes']
    fotoIn = request_data['foto']

    listaJson = []
    prolog = Prolog()
    prolog.consult("conocimientos.pl")
    for soln in prolog.query("receta(A,"+tipoIn+",C,D,E)"):
        listaPasos = ""
        listaIngredientes = ""
        for paso in soln["C"]:
            listaPasos += paso.value +","
        for ingrediente in soln["D"]:
            listaIngredientes += ingrediente.value+","

        listaJson.append({"nombre":soln["A"],"tipo":tipoIn,"pasos":listaPasos,"ingredientes":listaIngredientes,"foto":soln["E"][0].value})
        #listaJson.append(jsonify(nombre=soln["A"],tipo = soln["B"],pasos = soln["C"][0].value,ingredientes = soln["D"][0].value,foto=soln["E"][0].value))

    return Response(json.dumps(listaJson),  mimetype='application/json')

@app.route("/receta/ingrediente", methods=["POST"])
def getrecetaIngrediente():
    request_data = request.get_json()
    nombreIn = request_data['nombre']
    tipoIn = request_data['tipo']
    pasosIn = request_data['pasos']
    ingredienteIn = request_data['ingredientes']
    fotoIn = request_data['foto']
    insertar = 0;
    listaJson = []
    prolog = Prolog()
    prolog.consult("conocimientos.pl")
    prolog.assertz("buscarIng(Z,A,B,C,D,E):-receta(A,B,C,D,E),member2(Z,D)")
    for soln in prolog.query("buscarIng("+ingredienteIn+",A,B,C,D,E)"):
        listaPasos = ""
        listaIngredientes = ""
        for paso in soln["C"]:
            listaPasos += paso.value +","
        for ingrediente in soln["D"]:
            listaIngredientes += ingrediente.value+","

        for l in listaJson:
            if(l["nombre"]==soln["A"] and l["tipo"]==soln["B"]):
                insertar = 1
        if(insertar==1):
            insertar=0
        else:
            listaJson.append({"nombre":soln["A"],"tipo":soln["B"],"pasos":listaPasos,"ingredientes":listaIngredientes,"foto":soln["E"][0].value})

        #listaJson.append(jsonify(nombre=soln["A"],tipo = soln["B"],pasos = soln["C"][0].value,ingredientes = soln["D"][0].value,foto=soln["E"][0].value))

    return Response(json.dumps(listaJson),  mimetype='application/json')

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
