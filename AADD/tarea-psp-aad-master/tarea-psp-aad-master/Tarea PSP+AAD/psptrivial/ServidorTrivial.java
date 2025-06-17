package psptrivial;

import java.io.ObjectInputStream; // Importa la clase para leer objetos de una conexion
import java.io.ObjectOutputStream; // Importa la clase para enviar objetos a una conexion
import java.net.ServerSocket; // Importa la clase para crear un servidor que escucha conexiones
import java.net.Socket; // Importa la clase para manejar cada conexion individual
import java.util.concurrent.Semaphore; // Importa la clase para controlar el acceso a recursos compartidos
import java.util.ArrayList;
import preguntas.Pregunta; // Importa la clase Pregunta de otro paquete
import preguntas.Opcion; // Importa la clase Opcion de otro paquete
import java.util.Collections; // Importa la clase para trabajar con colecciones, por ejemplo para mezclar listas

public class ServidorTrivial {
    // Crea un semaforo que permite como maximo 2 conexiones simultaneas
    private static final Semaphore semaforo = new Semaphore(2);
    // Declara una lista para almacenar las preguntas del trivial
    private static ArrayList<Pregunta> listaPreguntas;

    public static void main(String[] args) { // Metodo principal que inicia el programa
        cargarPreguntas(); // Llama al metodo que carga las preguntas en la lista
        try (ServerSocket servidor = new ServerSocket(12345)) { // Crea un servidor que escucha en el puerto 12345
            System.out.println("ServidorTrivial iniciado en el puerto 12345"); // Muestra un mensaje de inicio en la consola
            while (true) { // Bucle infinito para aceptar conexiones de clientes
                Socket socket = servidor.accept(); // Espera y acepta la conexion de un cliente
                semaforo.acquire(); // Disminuye el semaforo para controlar el numero de conexiones simultaneas
                // Crea y lanza un nuevo hilo para atender al cliente que se ha conectado
                new Thread(new ManejadorCliente(socket)).start();
            }
        } catch (Exception e) { // Captura cualquier error que se produzca en el servidor
            e.printStackTrace(); // Imprime el error en la consola para facilitar la depuracion
        }
    }

    // Metodo que carga las preguntas en la lista
    private static void cargarPreguntas() {
        listaPreguntas = new ArrayList<>(); // Inicializa la lista de preguntas
        // Ejemplo de pregunta con sus opciones
        ArrayList<Opcion> opciones1 = new ArrayList<>(); // Crea una lista para las opciones de la pregunta
        opciones1.add(new Opcion("Respuesta A", true));
        opciones1.add(new Opcion("Respuesta C", false));
        opciones1.add(new Opcion("Respuesta D", false));
        // Crea una pregunta con un enunciado y las opciones, y la agrega a la lista de preguntas
        listaPreguntas.add(new Pregunta("¿Cual es la respuesta correcta?", opciones1));
        // Mezcla las preguntas para que aparezcan en orden aleatorio
        Collections.shuffle(listaPreguntas);
    }

    // Clase interna que maneja la comunicacion con cada cliente conectado
    private static class ManejadorCliente implements Runnable {
        private Socket socket; // Guarda la conexion del cliente

        // Constructor que recibe la conexion del cliente
        public ManejadorCliente(Socket socket) {
            this.socket = socket; // Asigna la conexion a la variable de la clase
        }

        @Override
        public void run() { // Metodo que se ejecuta cuando se lanza el hilo para el cliente
            try (
                    // Crea un canal para enviar objetos al cliente
                    ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                    // Crea un canal para recibir objetos del cliente
                    ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())
            ) {
                // Envia un mensaje al cliente pidiendole que introduzca su nombre
                salida.writeObject("Introduce tu nombre:");
                // Lee el nombre que envia el cliente
                String nombre = (String) entrada.readObject();
                // Muestra en la consola que un cliente se ha conectado junto con su nombre
                System.out.println("Cliente conectado: " + nombre);
                int aciertos = 0; // Inicializa el contador de respuestas correctas
                // Bucle para hacer hasta 5 preguntas o el numero de preguntas disponibles
                for (int i = 0; i < 5 && i < listaPreguntas.size(); i++) {
                    // Obtiene la pregunta actual de la lista
                    Pregunta p = listaPreguntas.get(i);
                    // Envia la pregunta al cliente (usa el metodo toString de la pregunta)
                    salida.writeObject(p.toString());
                    // Lee la respuesta que envia el cliente
                    String respuesta = (String) entrada.readObject();
                    // Convierte la respuesta a mayusculas y obtiene el indice segun la letra (A=0, B=1, etc.)
                    int indice = respuesta.toUpperCase().charAt(0) - 'A';
                    // Verifica si la respuesta es correcta: el indice debe estar en el rango y la opcion debe ser correcta
                    if (indice >= 0 && indice < p.getOpciones().size() && p.getOpciones().get(indice).isEsCorrecta()) {
                        aciertos++; // Incrementa el contador de respuestas correctas
                        salida.writeObject("Correcto!"); // Informa al cliente que su respuesta es correcta
                    } else {
                        // Informa al cliente que su respuesta es incorrecta y muestra la respuesta correcta
                        salida.writeObject("Incorrecto. La respuesta correcta es: " + obtenerRespuestaCorrecta(p));
                    }
                }
                // Informa al cliente que el juego ha terminado y muestra su puntuacion final
                salida.writeObject("Fin del juego. Tu puntuacion es: " + aciertos);
                // Muestra en la consola la puntuacion final del cliente
                System.out.println("Cliente " + nombre + " termino con " + aciertos + " aciertos.");
            } catch (Exception e) { // Captura errores durante la comunicacion con el cliente
                e.printStackTrace(); // Imprime el error en la consola
            } finally {
                try {
                    socket.close(); // Cierra la conexion con el cliente
                } catch (Exception ex) {
                    // Ignora errores que puedan ocurrir al cerrar la conexion
                }
                semaforo.release(); // Libera el semaforo para permitir nuevas conexiones
            }
        }

        // Metodo que devuelve la letra de la respuesta correcta de una pregunta
        private String obtenerRespuestaCorrecta(Pregunta p) {
            int i = 0; // Inicializa un contador para recorrer las opciones
            for (Opcion op : p.getOpciones()) { // Recorre cada opcion de la pregunta
                if (op.isEsCorrecta()) { // Si la opcion es la correcta
                    // Devuelve la letra correspondiente a la opcion ( A, B, C, ...)
                    return String.valueOf((char) ('A' + i));
                }
                i++; // Incrementa el contador para pasar a la siguiente opcion
            }
            return ""; // Si no se encuentra la opcion correcta, devuelve un string vacio
        }
    }
}
