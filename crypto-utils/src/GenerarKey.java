import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class GenerarKey {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator generador =
                KeyPairGenerator.getInstance("RSA");

        generador.initialize(2048);

        KeyPair pair =
                generador.generateKeyPair();

        try(FileOutputStream out =
                    new FileOutputStream("private.key")) {

            out.write(
                    pair.getPrivate().getEncoded()
            );
        }

        try(FileOutputStream out =
                    new FileOutputStream("public.key")) {

            out.write(
                    pair.getPublic().getEncoded()
            );
        }

        System.out.println("Claves generadas correctamente");
    }
}
