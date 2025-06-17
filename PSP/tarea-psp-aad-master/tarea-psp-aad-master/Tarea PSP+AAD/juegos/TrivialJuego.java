package juegos;

import java.util.Scanner;
import users.Partida;
import users.Player;

public class TrivialJuego {
    private Player jugador;
    private Partida partida;

    // Constructor que recibe el jugador y crea una nueva partida asociada a él
    public TrivialJuego(Player jugador) {
        this.jugador = jugador; // Asigna el jugador al atributo correspondiente
        this.partida = new Partida(jugador); // Crea una nueva partida con la fecha actual y puntuación 0
    }

    /**
     * (La carga de preguntas desde archivo se ha desactivado porque ahora se gestionan desde la BD).
     */
    public void jugar() {
        Scanner sc = new Scanner(System.in);

        // Se ha desactivado la carga de preguntas desde el fichero, ya que la gestion se hace en la base de datos.
        // ArrayList<Pregunta> preguntas = GestionaFicheros.cargaPreguntas();
        // if (preguntas.size() < 5) {
        //     System.out.println("No hay suficientes preguntas.");
        //     return; // Termina si no hay suficientes preguntas
        // }
        // Collections.shuffle(preguntas);
        // ArrayList<Pregunta> preguntasPartida = new ArrayList<>(preguntas.subList(0, 5));
        //
        // for (Pregunta p : preguntasPartida) {
        //     p.mezclarOpciones();
        //     System.out.println(p);
        //     System.out.print("Tu respuesta (A, B, C, D): ");
        //     String respuesta = sc.nextLine().toUpperCase();
        //     int index = respuesta.charAt(0) - 'A';
        //     if (index >= 0 && index < p.getOpciones().size()) {
        //         if (p.getOpciones().get(index).isEsCorrecta()) {
        //             System.out.println("¡Correcto!");
        //             partida.sumarPunto();
        //         } else {
        //             char correcta = 'A';
        //             for (int i = 0; i < p.getOpciones().size(); i++) {
        //                 if (p.getOpciones().get(i).isEsCorrecta()) {
        //                     correcta = (char) ('A' + i);
        //                     break;
        //                 }
        //             }
        //             System.out.println("Incorrecto. La respuesta correcta es: " + correcta);
        //         }
        //     } else {
        //         System.out.println("Opcion no valida.");
        //     }
        //     System.out.println();
        // }
        //
        // System.out.println("Puntuacion final: " + partida.getPuntuacion());
        // GestionaFicheros.guardaPartida(partida);


    }
}
