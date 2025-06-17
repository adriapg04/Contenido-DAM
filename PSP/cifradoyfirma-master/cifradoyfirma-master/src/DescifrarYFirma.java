import java.io.*;
import java.security.*;
import javax.crypto.Cipher;

public class DescifrarYFirma {
    public static void main(String[] args) {
        try {
            PublicKey publicKey = cargarClavePublica("publicKey");

            // Leer mensaje cifrado
            byte[] mensajeCifrado = leerArchivo("mensajeCifrado");

            // Descifrar el mensaje con la clave pública
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] mensajeDescifrado = cipher.doFinal(mensajeCifrado);
            String mensajeOriginal = new String(mensajeDescifrado);
            System.out.println("Mensaje descifrado: " + mensajeOriginal);

            // Verificar la firma digital
            byte[] firma = leerArchivo("firmaMensaje");
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(mensajeDescifrado);
            boolean esValida = signature.verify(firma);

            System.out.println("¿La firma es válida? " + esValida);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PublicKey cargarClavePublica(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PublicKey) ois.readObject();
        }
    }

    private static byte[] leerArchivo(String filename) throws IOException {
        return new FileInputStream(filename).readAllBytes();
    }
}
