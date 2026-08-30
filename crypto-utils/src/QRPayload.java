public class QRPayload{
    private String idQR;
    private double monto;
    private String referencia;
    private String expiracion;
    private boolean usado;
    
    public QRPayload(String idQR, double monto, String referencia, String expiracion, boolean usado){
        this.idQR = idQR;
        this.monto = monto;
        this.referencia = referencia;
        this.expiracion = expiracion;
        this.usado = usado;
    }
    
    @Override
    public String toString() {
        return "{"
                + "\"idQR\":\"" + idQR + "\","
                + "\"monto\":" + monto + ","
                + "\"referencia\":\"" + referencia + "\","
                + "\"expiracion\":\"" + expiracion + "\""
                + "}";
    }

    //GETTERS....
    public String getExpiracion() {
        return expiracion;
    }
    public String getIdQR() {
        return idQR;
    }
    public double getMonto() {
        return monto;
    }
    public String getReferencia() {
        return referencia;
    }
    public boolean isUsado() {
        return usado;
    }

}