package ficheros;

import java.io.*; // Importa las clases necesarias para operaciones de entrada/salida
import java.util.ArrayList;
import java.util.Scanner;
import preguntas.Pregunta;
import preguntas.Opcion;
import users.User;
import users.Partida;

public class GestionaFicheros {
    // Rutas relativas hacia los archivos de preguntas, usuarios y partidas en la carpeta "files"
    private static final String filePreguntas = "files/preguntas.txt";
    private static final String fileUsers = "files/user.dat";
    private static final String filePartidas = "files/partidas.txt";

    /**
     * Metodo estatico para guardar la lista de usuarios en un archivo binario
     * Utiliza ObjectOutputStream para serializar el objeto ArrayList<User>
     */
    public static void guardaUsers(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileUsers))) {
            // Escribe el objeto "users" en el archivo indicado
            oos.writeObject(users);
        } catch (IOException e) {
            // En caso de error se imprime la excepcion
            e.printStackTrace();
        }
    }

    /**
     * Metodo estatico para cargar la lista de usuarios desde el archivo binario
     * Utiliza ObjectInputStream para deserializar el objeto
     */
    public static ArrayList<User> cargaUsers() {
        ArrayList<User> users = new ArrayList<>(); // Crea una lista vacía
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileUsers))) {
            // Lee el objeto del archivo y lo convierte a ArrayList<User>
            users = (ArrayList<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se devuelve la lista vacia sin error
        } catch (IOException | ClassNotFoundException e) {
            // Captura otros errores de entrada/salida o de conversion y los imprime
            e.printStackTrace();
        }
        return users; // devuelve la lista de usuarios
    }

    /**
     * Metodo estatico para cargar las preguntas desde un archivo de texto
     * El formato esperado es:
     *   Linea 1: enunciado de la pregunta
     *   Linea 2: respuesta correcta
     *   Lineas 3-5: respuestas incorrectas
     */
    //Comentamos esto ya que ahora la gestion de datos la hacemos mediante SQL
    /* public static ArrayList<Pregunta> cargaPreguntas() {
        ArrayList<Pregunta> preguntas = new ArrayList<>(); // Lista que contendra las preguntas
        try (Scanner sc = new Scanner(new File(filePreguntas))) {
            // Mientras hayan lineas en el archivo..
            while (sc.hasNextLine()) {
                String enunciado = sc.nextLine(); // Lee el enunciado de la pregunta
                ArrayList<Opcion> opciones = new ArrayList<>(); // Crea una lista para las opciones
                if (sc.hasNextLine()) {
                    // Lee la respuesta correcta (se espera que sea la segunda linea)
                    String respuestaCorrecta = sc.nextLine();
                    // Crea una opcion marcada como correcta y la añade a la lista
                    opciones.add(new Opcion(respuestaCorrecta, true));
                }
                // Lee las siguientes tres lineas, que contienen respuestas incorrectas
                for (int i = 0; i < 3; i++) {
                    if (sc.hasNextLine()) {
                        String respuesta = sc.nextLine();
                        // Crea una opcion incorrecta y la añade a la lista
                        opciones.add(new Opcion(respuesta, false));
                    }
                }
                // Crea un objeto Pregunta con el enunciado y las opciones leidas, y lo añade a la lista
                preguntas.add(new Pregunta(enunciado, opciones));
            }
        } catch (IOException e) {
            // En caso de error al leer el archivo, imprime la traza de la excepción
            e.printStackTrace();
        }
        return preguntas; // Devuelve la lista de preguntas cargadas
    }

    /**
     * Metodo estatico para guardar (concatenar al final) una partida en el archivo de partidas
     */
    public static void guardaPartida(Partida partida) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePartidas, true))) {
            // Escribe la informacion de la partida en una nueva línea del archivo
            pw.println(partida.toString());
        } catch (IOException e) {
            // Si ocurre un error al escribir, se imprime la excepcion
            e.printStackTrace();
        }
    }

    /**
     * Metodo estatico para leer todas las partidas guardadas en el archivo de texto
     * Cada linea del archivo representa una partida
     */
    public static ArrayList<String> leePartidas() {
        ArrayList<String> partidas = new ArrayList<>(); // Lista que almacenará cada línea (partida)
        try (Scanner sc = new Scanner(new File(filePartidas))) {
            // Mientras haya líneas en el archivo, se añaden a la lista
            while (sc.hasNextLine()) {
                partidas.add(sc.nextLine());
            }
        } catch (IOException e) {
            // En caso de error, se imprime la excepcion
            e.printStackTrace();
        }
        return partidas; // Devuelve la lista de las partidas leidas
    }
}

