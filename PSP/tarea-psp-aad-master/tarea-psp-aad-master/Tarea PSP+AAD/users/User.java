package users;

import java.io.Serializable; // Se importa la interfaz Serializable para permitir que los objetos puedan ser escritos/guardados en un archivo

// Declaración de la clase abstracta User, que no se instancia directamente, sino que se extiende en subclases
public abstract class User implements Serializable {
    // Variables protegidas para almacenar el nombre de usuario y la contraseña
    protected String username; // Almacena el nombre de usuario
    protected String password; // Almacena la contraseña del usuario

    // Constructor que inicializa los atributos username y password
    public User(String username, String password) {
        this.username = username; // Asigna el nombre de usuario recibido al atributo
        this.password = password; // Asigna la contraseña recibida al atributo
    }

    // Metodo para cambiar la contraseña del usuario
    // La nueva contraseña debe tener al menos 8 caracteres
    public boolean cambiarPass(String newPass) {
        if(newPass.length() >= 8) { // Se verifica que la longitud de newPass sea al menos 8
            this.password = newPass; // Se actualiza el atributo password con la nueva contraseña
            return true; // Se retorna true indicando que el cambio fue exitoso
        }
        return false; // Si no se cumple la condicion, se retorna false sin cambiar la contraseña
    }

    // Metodo para comprobar si la contraseña introducida coincide con la almacenada
    public boolean compruebaPass(String pass) {
        return this.password.equals(pass); // Se compara la contraseña ingresada con la del objeto usando equals
    }

    // Metodo getter para obtener el nombre de usuario
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }


    // Metodo abstracto que debera ser implementado por las subclases (Player y Admin)
    // Permite saber si el usuario tiene permisos de administración
    public abstract boolean permisosAdmin();

    // Metodo toString que devuelve una representación en forma de cadena del objeto User
    @Override
    public String toString() {
        return "User: " + username; // Retorna el nombre de usuario precedido por "User: "
    }
}

