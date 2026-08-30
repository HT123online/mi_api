public class FirmaQR {
    public static void main(String[] args) throws Exception {
        
        if (args.length==0) {
            System.out.println("Falta payload");
            return;
        }
        String payload = args[0];
 
        RSA rsa = new RSA();
 
        String firma = rsa.firmar(payload);
 
        System.out.println(firma);
    }
}
