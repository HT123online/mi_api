import java.util.Base64;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class RSA {
    private PrivateKey clavePrivada;
    private PublicKey clavePublica;
    
    public RSA() throws Exception{
        KeyPairGenerator generador =KeyPairGenerator.getInstance("RSA");

        generador.initialize(2048);

        KeyPair par = generador.generateKeyPair(); //creamos tanto public key como private key

        clavePrivada = par.getPrivate(); //Se extrae y se guarda la Clave Privada
        clavePublica = par.getPublic(); // Se extrae y se guarda la Clave Publica
        
    }
    
    //Metodo firmar
    public String firmar(String payload) throws Exception{
        Signature firma = Signature.getInstance("SHA256withRSA");
 
        firma.initSign(clavePrivada);

        firma.update(payload.getBytes());

        byte bytesFirma[]= firma.sign();

        return Base64.getEncoder()
                .encodeToString(bytesFirma);
    }
    
    //Metodo verificar
    public boolean verificar(String payload, String firma) throws Exception{
        Signature verificador =
            Signature.getInstance(
                    "SHA256withRSA"
            );
 
        verificador.initVerify(clavePublica);

        verificador.update(payload.getBytes());

        return verificador.verify(
                Base64.getDecoder()
                        .decode(firma)
        );
        
    }
    
}
