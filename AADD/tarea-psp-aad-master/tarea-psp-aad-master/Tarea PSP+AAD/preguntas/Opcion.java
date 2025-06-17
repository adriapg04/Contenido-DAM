package preguntas;

public class Opcion { // Se define la clase Opcion, que representa la respuesta


    private String texto;
    // Variable que indica si esta opcion es la correcta (true = correcta, false = incorrecta)
    private boolean esCorrecta;

    // Constructor que recibe el texto de la opcion y un valor booleano que indica si es correcta
    public Opcion(String texto, boolean esCorrecta) {
        this.texto = texto; // Asigna el texto recibido a la variable "texto"
        this.esCorrecta = esCorrecta; // Asigna el valor booleano a la variable "esCorrecta"
    }

    // Metodo getter: Devuelve el texto de la opcion
    public String getTexto() {
        return texto; // Devuelve el valor almacenado en "texto"
    }

    // Metodo getter: Devuelve el valor que indica si la opcion es correcta
    public boolean isEsCorrecta() {
        return esCorrecta; // Devuelve el valor almacenado en "esCorrecta"
    }

    // Metodo toString: Devuelve el texto de la opcion cuando se necesite una representacion en cadena
    @Override
    public String toString() {
        return texto; // Devuelve el texto que representa esta opcion
    }
}
