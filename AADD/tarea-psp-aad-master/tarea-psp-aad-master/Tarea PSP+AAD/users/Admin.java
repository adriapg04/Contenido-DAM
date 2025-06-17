package users;

// La clase Admin extiende de la clase User, representando a un usuario con permisos de administrador
public class Admin extends User {

    // Constructor que recibe el nombre y la contraseña, llamando al constructor de la clase User
    public Admin(String username, String password) {
        super(username, password); // Inicializa los atributos de la superclase
    }

    // Implementacion del metodo abstracto permisosAdmin() para Admin
    // Devuelve true para indicar que este usuario tiene permisos administrativos
    @Override
    public boolean permisosAdmin() {
        return true;
    }

    // Metodo toString que devuelve una representacion del objeto Admin
    @Override
    public String toString() {
        return "Admin: " + username; // Retorna el nombre de usuario con el prefijo "Admin: "
    }
}
