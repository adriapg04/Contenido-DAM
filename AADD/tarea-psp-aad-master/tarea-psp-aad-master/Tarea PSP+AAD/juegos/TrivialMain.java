package juegos;

import java.util.Scanner;
import dao.UserDAO;
import dao.UserDAOImpl;
import users.Admin;
import users.Player;
import users.User;

//Clase principal de Trivial, que gestiona el registro e inicio de sesion de usuarios.
public class TrivialMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Crea una instancia del DAO para gestionar la persistencia en la base de datos
        UserDAO userDAO = new UserDAOImpl();

        // Declara una variable para almacenar la opcion seleccionada en el menu
        int opcion;
        // Declara una variable booleana para saber si el usuario ha iniciado sesion correctamente
        boolean loggedIn = false;
        // Declara una variable para almacenar el usuario actual (registrado o logueado)
        User userActual = null;

        // Inicia un bucle que se repetira hasta que el usuario inicie sesion o elija salir
        do {
            // Muestra el menu de opciones al usuario
            System.out.println("Menu:");
            System.out.println("1. Registro player");
            System.out.println("2. Registro admin");
            System.out.println("3. Inicio de sesion");
            System.out.println("4. Salir");
            // Solicita al usuario que elija una opcion
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            // Evalua la opcion seleccionada mediante una estructura switch
            switch (opcion) {
                case 1:
                    // Opcion 1: Registro de un jugador (player)
                    // Llama al metodo registrar con el parametro false (no admin) y almacena el resultado en userActual
                    userActual = registrar(false, sc, userDAO);
                    // Si el registro fue exitoso, marca loggedIn como true
                    if (userActual != null) {
                        loggedIn = true;
                    }
                    break;
                case 2:
                    // Opcion 2: Registro de un administrador (admin)
                    // Llama al metodo registrar con el parametro true (admin) y almacena el resultado en userActual
                    userActual = registrar(true, sc, userDAO);
                    // Si el registro fue exitoso, marca loggedIn como true
                    if (userActual != null) {
                        loggedIn = true;
                    }
                    break;
                case 3:
                    // Opcion 3: Inicio de sesion de un usuario ya registrado
                    // Llama al metodo login y almacena el usuario autenticado en userActual
                    userActual = login(sc, userDAO);
                    // Si el inicio de sesion fue correcto, marca loggedIn como true
                    if (userActual != null) {
                        loggedIn = true;
                    }
                    break;
                case 4:
                    // Opcion 4: Salir de la aplicacion
                    System.out.println("Saliendo...");
                    break;
                default:
                    // Si se ingresa una opcion no valida, muestra un mensaje de error
                    System.out.println("Opcion no valida.");
            }
        } while (!loggedIn && opcion != 4);
        // El bucle finaliza cuando el usuario ha iniciado sesion o ha elegido salir

        // Si el usuario se ha autenticado correctamente, se procede a iniciar el panel de administracion o el juego
        if (loggedIn) {
            // Verifica si el usuario tiene permisos de admin
            if (userActual.permisosAdmin()) {
                // Si es administrador, crea un objeto TrivialAdmin y llama a su metodo administrar
                TrivialAdmin admin = new TrivialAdmin();
                admin.administrar();
            } else {
                // Si es player, crea un objeto TrivialJuego y llama a su metodo jugar
                TrivialJuego juego = new TrivialJuego((Player) userActual);
                juego.jugar();
            }
        }
    }

    /**
     * Metodo para registrar un usuario (player o admin) y guardarlo en la base de datos mediante el DAO.
     *
     * @param esAdmin Indica si se registra un administrador (true) o un jugador (false)
     * @param sc Scanner para leer datos del usuario
     * @param userDAO DAO para gestionar la persistencia de usuarios
     * @return Devuelve el usuario registrado o null si ocurre algun error
     */
    private static User registrar(boolean esAdmin, Scanner sc, UserDAO userDAO) {
        // Solicita al usuario que introduzca su nombre
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine();

        // Verifica si ya existe un usuario con ese nombre en la base de datos
        User existente = userDAO.getUserByUsername(nombre);
        if (existente != null) {
            // Si el usuario ya existe, muestra un mensaje y devuelve null
            System.out.println("El usuario ya existe.");
            return null;
        }

        // Solicita al usuario que introduzca su contraseña
        System.out.print("Introduce la contrasena: ");
        String pass = sc.nextLine();
        // Solicita que se repita la contraseña para confirmar
        System.out.print("Repite la contrasena: ");
        String repPass = sc.nextLine();

        // Verifica que ambas contraseñas sean iguales
        if (!pass.equals(repPass)) {
            // Si no coinciden, muestra un mensaje y devuelve null
            System.out.println("Las contrasenas no coinciden.");
            return null;
        }

        // Verifica que la contraseña tenga al menos 8 caracteres
        if (pass.length() < 8) {
            // Si la contraseña es demasiado corta, muestra un mensaje y devuelve null
            System.out.println("La contrasena debe tener al menos 8 caracteres.");
            return null;
        }

        // Declara una variable para el nuevo usuario
        User nuevoUser;
        if (esAdmin) {
            // Si se registra como administrador, crea un objeto Admin
            nuevoUser = new Admin(nombre, pass);
        } else {
            // Si se registra como jugador, crea un objeto Player
            nuevoUser = new Player(nombre, pass);
        }

        // Guarda el nuevo usuario en la base de datos mediante el DAO
        boolean creado = userDAO.createUser(nuevoUser);
        if (creado) {
            // Si se registro exitosamente, muestra un mensaje y retorna el usuario creado
            System.out.println("Usuario registrado exitosamente.");
            return nuevoUser;
        } else {
            // Si ocurre un error al registrar el usuario, muestra un mensaje y devuelve null
            System.out.println("Error al registrar el usuario.");
            return null;
        }
    }

    /**
     * Metodo para iniciar sesion de un usuario registrado, consultandolo en la base de datos.
     *
     * @param sc Scanner para leer los datos de entrada
     * @param userDAO DAO para gestionar la persistencia de usuarios
     * @return Devuelve el usuario autenticado o null si las credenciales son incorrectas
     */
    private static User login(Scanner sc, UserDAO userDAO) {
        // Solicita al usuario que introduzca su nombre
        System.out.print("Introduce el nombre: ");
        String nombre = sc.nextLine();
        // Solicita al usuario que introduzca su contraseña
        System.out.print("Introduce la contrasena: ");
        String pass = sc.nextLine();

        // Consulta la base de datos para obtener el usuario con ese nombre mediante el DAO
        User usuario = userDAO.getUserByUsername(nombre);
        // Verifica que el usuario exista y que la contraseña sea correcta
        if (usuario != null && usuario.compruebaPass(pass)) {
            // Si las credenciales son correctas, muestra un mensaje y retorna el usuario
            System.out.println("Inicio de sesion correcto.");
            return usuario;
        } else {
            // Si las credenciales son incorrectas, muestra un mensaje y devuelve null
            System.out.println("Credenciales incorrectas.");
            return null;
        }
    }
}
