package juegos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import ficheros.GestionaFicheros;
import preguntas.Pregunta;
import users.Partida;
import users.Player;

public class TrivialJuego {
    private Player jugador;
    private Partida partida;
    
    // Constructor que recibe el jugador y crea una nueva partida asociada a el
    public TrivialJuego(Player jugador) {
        this.jugador = jugador; // Asigna el jugador al atributo correspondiente
        this.partida = new Partida(jugador); // Crea una nueva partida con la fecha actual y puntuacion 0
    }
    
    /**
     * Metodo que gestiona el juego completo:
     * - Carga las preguntas desde el archivo
     * - Selecciona 5 preguntas aleatorias
     * - Muestra cada pregunta con las opciones en orden aleatorio
     * - Solicita la respuesta del usuario y actualiza la puntuación
     * - Al finalizar, muestra la puntuacion final y guarda la partida en el archivo
     */
    public void jugar() {
        Scanner sc = new Scanner(System.in);
        // Carga todas las preguntas disponibles
        ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
        // Verifca que haya al menos 5 preguntas
        if (preguntas.size() < 5) {
            System.out.println("No hay suficientes preguntas.");
            return; // Termina si no hay suficientes preguntas
        }
        // Mezcla la lista de preguntas para seleccionar 5 aleatoriamente
        Collections.shuffle(preguntas);
        // Selecciona las primeras 5 preguntas de la lista mezclada
        ArrayList<Pregunta> preguntasPartida = new ArrayList<>(preguntas.subList(0, 5));
        
        // Recorre cada una de las 5 preguntas seleccionadas
        for (Pregunta p : preguntasPartida) {
            p.mezclarOpciones(); // Mezcla las opciones de respuesta para presentarlas en orden aleatorio
            System.out.println(p); // Imprime la pregunta y sus opciones usando el método toString()
            System.out.print("Tu respuesta (A, B, C, D): ");
            String respuesta = sc.nextLine().toUpperCase(); // Lee la respuesta del usuario y la convierte a mayusculas
            // Convierte la letra de la respuesta a un indice (A=0, B=1, etc.)
            int index = respuesta.charAt(0) - 'A';
            // Verifica que el índice este dentro del rango de opciones disponibles
            if (index >= 0 && index < p.getOpciones().size()) {
                // Si la opcion seleccionada es correcta, se muestra un mensaje y se suma un punto
                if (p.getOpciones().get(index).isEsCorrecta()) {
                    System.out.println("¡Correcto!");
                    partida.sumarPunto(); // Incrementa la puntuacion de la partida
                } else {
                    // Si la respuesta es incorrecta, se busca cual era la opcion correcta
                    char correcta = 'A'; // Variable para almacenar la letra de la respuesta correcta
                    for (int i = 0; i < p.getOpciones().size(); i++) {
                        if (p.getOpciones().get(i).isEsCorrecta()) {
                            correcta = (char) ('A' + i); // Calcula la letra correspondiente al índice correcto
                            break; // Se rompe el ciclo al encontrar la respuesta correcta
                        }
                    }
                    // Se informa al usuario de cual era la respuesta correcta
                    System.out.println("Incorrecto. La respuesta correcta es: " + correcta);
                }
            } else {
                System.out.println("Opción no válida.");
            }
            System.out.println();
        }
        // Al finalizar las 5 preguntas, se muestra la puntuacion final obtenida
        System.out.println("Puntuación final: " + partida.getPuntuacion());
        // Se guarda la partida en el archivo de partidas
        GestionaFicheros.guardaPartida(partida);
    }
}
