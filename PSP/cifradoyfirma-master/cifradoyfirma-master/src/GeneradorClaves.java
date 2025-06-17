import java.io.*;
import java.security.*;

public class GeneradorClaves {
    public static void main(String[] args) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();

            // Guardar la clave privada
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("privateKey"))) {
                oos.writeObject(keyPair.getPrivate());
            }

            // Guardar la clave pública
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("publicKey"))) {
                oos.writeObject(keyPair.getPublic());
            }

            System.out.println("Claves generadas y guardadas correctamente.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
