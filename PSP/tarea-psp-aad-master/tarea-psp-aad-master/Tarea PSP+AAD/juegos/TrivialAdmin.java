package juegos;

import java.util.ArrayList;
import java.util.Collections; // Importa la clase Collections para mezclar listas
import ficheros.GestionaFicheros; // Importa la clase GestionaFicheros para leer/escribir en ficheros
import users.User; // Importa la clase User, que representa a un usuario registrado

public class TrivialAdmin {

    // Metodo que muestra las partidas jugadas y los nombres de todos los usuarios registrados
    // Los usuarios se ordenan alfabeticamente
    public void administrar() {
        System.out.println("== Panel de Administrador =="); // Muestra el encabezado del panel
        System.out.println("Partidas jugadas:"); // Muestra el titulo para las partidas jugadas

        // Se lee el listado de partidas almacenadas en un ArrayList de tipo String
        ArrayList<String> partidas = GestionaFicheros.leePartidas();

        // Recorre cada partida en la lista y la imprime en consola
        for (String partida : partidas) {
            System.out.println(partida);
        }

        System.out.println("\nUsuarios registrados:"); // Muestra el titulo para los usuarios registrados

        // Se cargan los usuarios registrados en un ArrayList de User
        ArrayList<User> users = GestionaFicheros.cargaUsers();

        // Se ordena la lista de usuarios alfabeticamente segun el nombre de usuario
        Collections.sort(users, (u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));

        // Recorre la lista de usuarios y muestra solo el nombre de cada usuario
        for (User u : users) {
            System.out.println(u.getUsername());
        }
    }
}
