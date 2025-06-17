package users;

import java.util.Date;

// La clase Partida representa una sesion de juego
public class Partida {
    private Date fecha;     // Almacena la fecha y hora en que se inicio la partida
    private Player jugador; // Guarda el jugador que esta participando en la partida
    private int puntuacion; // Almacena la puntuacion obtenida en la partida

    // Constructor que inicializa la partida con el jugador y la fecha actual
    public Partida(Player jugador) {
        this.fecha = new Date(); // Se asigna la fecha y hora actual a la partida
        this.jugador = jugador;  // Se asigna el jugador que esta jugando
        this.puntuacion = 0;     // La puntuación inicia en 0
    }

    // Metodo para sumar un punto a la puntuación de la partida
    public void sumarPunto() {
        puntuacion++; // Incrementa en 1 la puntuación
    }

    // Metodo getter para obtener la fecha de la partida
    public Date getFecha() {
        return fecha;
    }

    // Metodo getter para obtener el jugador que participo en la partida
    public Player getJugador() {
        return jugador;
    }

    // Metodo getter para obtener la puntuacion final de la partida
    public int getPuntuacion() {
        return puntuacion;
    }

    // Metodo toString que devuelve la informacion de la partida en forma de cadena
    @Override
    public String toString() {
        // Combina la fecha, el nombre del jugador y la puntuación en un solo String
        return fecha.toString() + " - " + jugador.getUsername() + " - " + puntuacion;
    }
}

