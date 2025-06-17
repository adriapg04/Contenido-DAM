package juegos;

import java.util.ArrayList;
import java.util.Scanner;
import ficheros.GestionaFicheros;
import users.Admin;
import users.Player;
import users.User;

public class TrivialMain {
    // Arraylist estatico que contendra la lista de usuarios cargados o registrados
    private static ArrayList<User> users;
    
    // Método main: punto de entrada de la aplicacion
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Se carga la lista de usuarios previamente guardada en el archivo (si existe)
        users = GestionaFicheros.cargaUsers();
        int opcion; // Variable para almacenar la opcion seleccionada en el menu
        boolean loggedIn = false; // variasble para saber si el usuario ha iniciado sesion correctamente
        User userActual = null;   // Variable para almacenar el usuario que inicia sesion o se registra
        
        // Bucle para mostrar el menu hasta que el usuario inicie sesion o decida salir
        do {
            System.out.println("Menu:"); // Muestra el menu de opciones
            System.out.println("1. Registro player");
            System.out.println("2. Registro admin");
            System.out.println("3. Inicio de sesión");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            
            // Se evaluan las opciones con un switch case
            switch (opcion) {
                case 1:
                    // Opcion 1: Registro de un jugador
                    userActual = registrar(false, sc); // Llama al metodo registrar indicando que no es admin
                    if (userActual != null) { // Si el registro fue exitoso (no se devolvio null)
                        loggedIn = true; // Se marca que el usuario ha iniciado sesión
                    }
                    break;
                case 2:
                    // Opcion 2: Registro de un administrador
                    userActual = registrar(true, sc); // Llama al metodo registrar indicando que es admin
                    if (userActual != null) { // Si el registro fue exitoso
                        loggedIn = true; // Se marca que el usuario ha iniciado sesión
                    }
                    break;
                case 3:
                    // Opcion 3: Inicio de sesion de un usuario ya registrado
                    userActual = login(sc); // Llama al metodo login
                    if (userActual != null) { // Si las credenciales son correctas
                        loggedIn = true; // Se marca que el usuario ha iniciado sesion
                    }
                    break;
                case 4:
                    // Opcion 4: Salir de la aplicacion
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!loggedIn && opcion != 4); // El bucle continua hasta que se inicie sesion o se elija salir
        
        if (loggedIn) { // Si el usuario se ha autenticado correctamente
            // Se actualiza el archivo de usuarios para reflejar cualquier cambio (registro nuevo)
            GestionaFicheros.guardaUsers(users);
            // Dependiendo de los permisos del usuario, se inicia el juego o se muestra el panel de admin
            if (userActual.permisosAdmin()) {
                // Si el usuario es administrador, se crea un objeto TrivialAdmin y se llama a administrar()
                TrivialAdmin admin = new TrivialAdmin();
                admin.administrar();
            } else {
                // Si el usuario es jugador, se crea un objeto TrivialJuego y se inicia el juego
                TrivialJuego juego = new TrivialJuego((Player) userActual);
                juego.jugar();
            }
        }
        
    }
    
    /**
     * Metodo privado para registrar un usuario.
     * @param esAdmin Indica si se esta registrando un administrador (true) o un jugador (false)
     * @param sc Objeto Scanner para leer la entrada del usuario
     * @return Devuelve el usuario registrado o null si hubo algún error en el registro
     */
    private static User registrar(boolean esAdmin, Scanner sc) {
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine(); // Lee el nombre de usuario
        
        // Verifica que no exista otro usuario con el mismo nombre (sin distinguir mayusculas/minusculas)
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(nombre)) {
                System.out.println("El usuario ya existe.");
                return null; // Si el usuario ya existe, se devurlve null
            }
        }
        System.out.print("Introduce la contraseña: ");
        String pass = sc.nextLine(); // Lee la contraseña
        System.out.print("Repite la contraseña: ");
        String repPass = sc.nextLine(); // Lee la confirmacion de la contraseña
        
        // Verifica que ambas contraseñas sean iguales
        if (!pass.equals(repPass)) {
            System.out.println("Las contraseñas no coinciden.");
            return null; // devuelve null si las contraseñas no coinciden
        }
        // Verifica que la contraseña tenga al menos 8 caracteres
        if (pass.length() < 8) {
            System.out.println("La contraseña debe tener al menos 8 caracteres.");
            return null; // Devuelve null si la contraseña es muy corta
        }
        User nuevoUser; // Declaracion de la variable para el nuevo usuario
        if (esAdmin) {
            // Si es administrador, crea una instancia de Admin
            nuevoUser = new Admin(nombre, pass);
        } else {
            // Si es jugador, crea una instancia de Player
            nuevoUser = new Player(nombre, pass);
        }
        // Agrega el nuevo usuario a la lista de usuarios
        users.add(nuevoUser);
        System.out.println("Usuario registrado exitosamente.");
        return nuevoUser; // Devuelve el usuario creado
    }
    
    /**
     * Metodo privado para el inicio de sesion de un usuario registrado.
     * @param sc Objeto Scanner para leer la entrada del usuario
     * @return Devuelve el usuario autenticado o null si las credenciales son incorrectas
     */
    private static User login(Scanner sc) {
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine(); // Lee el nombre de usuario ingresado
        System.out.print("Introduce la contraseña: ");
        String pass = sc.nextLine(); // Lee la contraseña ingresada
        // Recorre la lista de usuarios para buscar una coincidencia
        for (User u : users) {
            // Se compara el nombre (ignorando mayusculas/minusculas) y se comprueba la contraseña
            if (u.getUsername().equalsIgnoreCase(nombre) && u.compruebaPass(pass)) {
                System.out.println("Inicio de sesión correcto.");
                return u; // devuelve el usuario si las credenciales son correctas
            }
        }
        System.out.println("Credenciales incorrectas.");
        return null; // devuelve null si no se encuentra ninguna coincidencia
    }
}
