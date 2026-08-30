import java.util.Base64;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.*;

public class RSA {
    private static KeyPair keyPair;

    static {
        try {

            KeyPairGenerator generador =
                    KeyPairGenerator.getInstance("RSA");

            generador.initialize(2048);

            keyPair =
                    generador.generateKeyPair();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private PrivateKey clavePrivada =
            keyPair.getPrivate();

    private PublicKey clavePublica =
            keyPair.getPublic();
    
    
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
