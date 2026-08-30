from flask import Flask, send_from_directory,jsonify,request,send_file
import qrcode
import json #cambio n1
import uuid
from io import BytesIO
from flasgger import Swagger


app=Flask(__name__)
swagger = Swagger(app)

@app.route('/')
def saludo():
    return("si lees esto es porque quiero pan con queso :v")

@app.route('/qr/<texto>/<temporal>')
def generaQR(texto,temporal):
    
    qr=qrcode.make(texto)

    img_io=BytesIO()
    qr.save(img_io,'PNG')
    img_io.seek(0)

    return send_file(img_io,
                     mimetype='image/png')

@app.route("/generar_qr/<monto>/<ref>",methods=["POST"])

def pross(monto,ref):
    """
    Generar QR con monto y referencia
    ---
    tags:
      - QR Code
    parameters:
      - name: monto
        in: path
        type: number
        required: true
        description: Monto de la transacción
      - name: ref
        in: path
        type: string
        required: true
        description: Referencia
    responses:
      200:
        description: QR generado
        content:
          image/png:
            schema:
              type: string
              format: binary
    """
    data={'monto':monto,
          'compradas':ref}
    return jsonify(data)



def guardarQr(nuevo_qr): #cambio n2
    with open("qr_data.json", "r") as archivo:
        datos = json.load(archivo)
 
    for qr in datos["qrs"]:
 
        if qr["idQR"] == nuevo_qr["idQR"]:
            print("QR ya existe")
            return False
 
    datos["qrs"].append(nuevo_qr)
 
    with open("qr_data.json", "w") as archivo:
        json.dump(datos, archivo, indent=4)
 
    return True

def buscarQr(idQR): #cambio n3 importante para ver si es el mismoqr q ya enviaron

    with open("qr_data.json", "r") as archivo:
        datos = json.load(archivo)

    for qr in datos["qrs"]:

        if qr["idQR"] == idQR:
            return qr

    return None

def marUsado(idQR): #cambio n3

    with open("qr_data.json", "r") as archivo:
        datos = json.load(archivo)

    for qr in datos["qrs"]:

        if qr["idQR"] == idQR:

            qr["usado"] = True

            with open("qr_data.json", "w") as archivo:
                json.dump(datos, archivo, indent=4)

            return True

    return False
qr = buscarQr("QR123")

def verReplayAttack(idQR): #cambio n4
    qr = buscarQr(idQR)

    if qr is None:
        return False

    return qr["usado"]

def procesarPago(idQR): #cambio n4

    if verReplayAttack(idQR):
        return "Replay Attack"

    marUsado(idQR)

    return "Pago Aceptado"


if __name__=="__main__":
    app.run(debug=True)

# guardarQr({
#     "idQR": "QR666",
#     "monto": 40,
#     "referencia": "hamburguesa",
#     "expiracion": "2026-08-30",
#     "usado": False
# })
# print(procesarPago("QR992"))
# print(procesarPago("QR992"))