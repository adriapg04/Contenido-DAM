package psptrivial;

import java.io.ObjectInputStream; // Importa la clase que permite leer objetos de una conexion
import java.io.ObjectOutputStream; // Importa la clase que permite enviar objetos a una conexion
import java.net.Socket; // Importa la clase que maneja la conexion entre el cliente y el servidor
import java.util.Scanner;

/**
 * ClienteTrivial: implementa el cliente del Trivial.
 * Se conecta al servidor en localhost:12345 y sigue el protocolo de comunicacion.
 */
public class ClienteTrivial { // Define la clase principal del cliente
    public static void main(String[] args) { // Metodo principal que se ejecuta al iniciar el programa
        // Se usa try-with-resources para asegurarse de que los recursos se cierren automaticamente
        try (Socket socket = new Socket("localhost", 12345); // Crea una conexion al servidor en el puerto 12345
             ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream()); // Crea el canal para enviar datos al servidor
             ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream()); // Crea el canal para recibir datos del servidor
             Scanner sc = new Scanner(System.in)) { // Crea un objeto para leer lo que el usuario escribe

            // Lee el primer mensaje del servidor y lo muestra en la consola
            System.out.println((String) entrada.readObject());
            // Lee el nombre que el usuario introduce desde el teclado
            String nombre = sc.nextLine();
            // Envia el nombre del usuario al servidor
            salida.writeObject(nombre);

            // Bucle para procesar 5 rondas de preguntas
            for (int i = 0; i < 5; i++) {
                // Lee una pregunta enviada por el servidor
                String pregunta = (String) entrada.readObject();
                // Muestra la pregunta en la consola para que el usuario la vea
                System.out.println(pregunta);
                // Lee la respuesta que el usuario escribe
                String respuesta = sc.nextLine();
                // Envia la respuesta del usuario al servidor
                salida.writeObject(respuesta);
                // Lee la evaluacion del servidor (si la respuesta fue correcta o no)
                String evaluacion = (String) entrada.readObject();
                // Muestra la evaluacion en la consola
                System.out.println(evaluacion);
            }
            // Lee el mensaje final del servidor, que indica el fin del juego y la puntuacion final
            String mensajeFinal = (String) entrada.readObject();
            // Muestra el mensaje final en la consola
            System.out.println(mensajeFinal);
        } catch (Exception e) { // Captura cualquier error que ocurra durante la ejecucion
            e.printStackTrace(); // Imprime en la consola la informacion del error
        }
    }
}
