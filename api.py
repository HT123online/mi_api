from flask import Flask, send_from_directory,jsonify,request,send_file
import qrcode
from io import BytesIO

app=Flask(__name__)
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

@app.route("/generar/<monto>/<ref>",methods=["POST"])
def pross(monto,ref):
    data={'monto':monto,
          'compradas':ref}
    return jsonify(data)


if __name__=="__main__":
    app.run(debug=True)