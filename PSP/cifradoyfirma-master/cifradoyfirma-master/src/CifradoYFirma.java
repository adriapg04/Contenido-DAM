import java.io.*;
import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class CifradoYFirma {
    public static void main(String[] args) {
        try {
            PrivateKey privateKey = cargarClavePrivada("privateKey");
            String mensaje = "Este es un mensaje secreto";
            byte[] mensajeBytes = mensaje.getBytes("UTF-8");

            // Cifrado con clave privada
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] mensajeCifrado = cipher.doFinal(mensajeBytes);
            guardarArchivo("mensajeCifrado", mensajeCifrado);

            // Firma digital
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(mensajeBytes);
            byte[] firma = signature.sign();
            guardarArchivo("firmaMensaje", firma);

            System.out.println("Mensaje cifrado y firmado correctamente.");
            System.out.println("Firma generada (Base64): " + Base64.getEncoder().encodeToString(firma));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PrivateKey cargarClavePrivada(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PrivateKey) ois.readObject();
        }
    }

    private static void guardarArchivo(String filename, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        }
    }
}
