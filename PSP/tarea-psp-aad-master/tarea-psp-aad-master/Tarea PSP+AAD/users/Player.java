package users;


public class Player extends User {

    // Constructor de la clase Player que llama al constructor de la superclase User
    public Player(String username, String password) {
        super(username, password); // Llama al constructor de User para inicializar username y password
    }

    // Implementacion del metodo abstracto permisosAdmin() para Player
    // Para un jugador, este metodo siempre devuelve false
    @Override
    public boolean permisosAdmin() {
        return false;
    }

    // Metodo toString que devuelve una cadena representativa del objeto Player
    @Override
    public String toString() {
        return "Player: " + username; // Retorna el nombre de usuario con el prefijo "Player: "
    }
}

