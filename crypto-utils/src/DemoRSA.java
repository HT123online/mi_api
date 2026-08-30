public class DemoRSA {

    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA();
        CryptoUtils crypto = new CryptoUtils();

        //Ejemplo de payload v.08 java
        // String payload =
        // "{"
        // + "\"monto\":100,"
        // + "\"cuenta\":\"123456\""
        // + "}";

        QRPayload qrMalo = new QRPayload("qr123",5000,"hamburguesa","2026-08-29", false);

        //Pruebas pasadas
        // String firma = rsa.firmar(payload);

        // System.out.println(firma);

        // boolean valida = rsa.verificar(payMalo,firma);

        // System.out.println(valida);

        QRPayload qr = new QRPayload(
            "qr123",
            40,
            "hamburguesa",
            "2026-08-29",
            false
        );

        System.out.println(qr);

        String firma =rsa.firmar(qr.toString());

        boolean valida =
        rsa.verificar(
                qrMalo.toString(),
                firma
        );
        System.out.println("----------------------------Firma:");
        System.out.println(firma);

        System.out.println("----------------------------Validacion");
        System.out.println(valida);

        //Probando la Regla del Monto Valido
        System.out.println("PROBANDOOOOOOOO DEBERIA SALIR: true, false, false");
        // System.out.println(crypto.montoValido(40));

        // System.out.println(crypto.montoValido(-50));

        // System.out.println(crypto.montoValido(0));

        //Probandooo la regla de Vencimiento con STRINGS sin LocalDate
        // System.out.println("PROBANDOOOOOOOO DEBERIA SALIR: false");
        // CryptoUtils cryptox = new CryptoUtils();

        // System.out.println(cryptox.expiro("2026-08-30"));

        //Validando el objeto QRPayload
        System.out.println(crypto.payloadValido(qr));
    }
}
