public class VerificarQR {

    public static void main(String[] args) throws Exception {

        if(args.length < 2){
            System.out.println("Faltan datos");
            return;
        }
 
        String payload = args[0];
        String firma = args[1];
 
        RSA rsa = new RSA();
 
        boolean valido =
                rsa.verificar(payload, firma);
 
        System.out.println(valido);
    }
}

