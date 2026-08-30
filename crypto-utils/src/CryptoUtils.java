import java.time.LocalDate;

public class CryptoUtils {
    private RSA rsaa;
    // public CryptoUtils() throws Exception{
    //     rsaa = new RSA();
    // }
    public boolean montoValido(double monto){
        return monto>0;
    }

    public boolean expiro(String fechaExp){
    
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaExpiracion = LocalDate.parse(fechaExp);
    
        if (fechaExpiracion.isBefore(fechaActual)) {
            return true;
        }
        return false;
    }
    
    public boolean usado(boolean usado){
        return usado;
    }

    public boolean payloadValido(QRPayload qr){
        //El QR es valido si es que el monto es aceptable, no evencio y no esta usado.
        return montoValido(qr.getMonto()) && !expiro(qr.getExpiracion()) && !usado(qr.isUsado());
    }

    //Intento de borrar los throws Exception
    public boolean generarFirmaSegura(QRPayload qr){
        try{

            rsaa.firmar(qr.toString());

            return true;

        }catch(Exception e){

            System.out.println(
                "Error firmando QR"
            );

            return false;
        }
    }
}
