import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


public class RSA {
    
    private PrivateKey clavePrivada;
    private PublicKey clavePublica;
    
    public RSA() throws Exception {
    
        byte[] privateBytes =
                Files.readAllBytes(
                        Paths.get("crypto-utils/src/private.key")
                );
    
        byte[] publicBytes =
                Files.readAllBytes(
                        Paths.get("crypto-utils/src/public.key")
                );
    
        KeyFactory factory =
                KeyFactory.getInstance("RSA");
    
        clavePrivada =
                factory.generatePrivate(
                        new PKCS8EncodedKeySpec(privateBytes)
                );
    
        clavePublica =
                factory.generatePublic(
                        new X509EncodedKeySpec(publicBytes)
                );
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
