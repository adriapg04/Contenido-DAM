package juegos;

import java.util.ArrayList;
import java.util.Collections;
import ficheros.GestionaFicheros;
import users.User;

public class TrivialAdmin {
    
    /**
     * Metodo que muestra todas las partidas jugadas y los nombres de todos los usuarios registrados.
     * Los usuarios se ordenan alfabeticmente.
     */
    public void administrar() {
        System.out.println("=== Panel de Administrador ===");
        System.out.println("Partidas jugadas:");
        // Se lee el listado de partidas almacenadas
        ArrayList<String> partidas = GestionaFicheros.leePartidas();
        // Se recorre cada partida y se muestra
        for (String partida : partidas) {
            System.out.println(partida);
        }
        System.out.println("\nUsuarios registrados:");
        // Se cargan los usuarios registrados
        ArrayList<User> users = GestionaFicheros.cargaUsers();
        // Se ordena la lista de usuarios alfabticamente segun el nombre de usuario
        Collections.sort(users, (u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));
        // Se recorre la lista y se muestra solo el nombre de cada usuario
        for (User u : users) {
            System.out.println(u.getUsername());
        }
    }
}
