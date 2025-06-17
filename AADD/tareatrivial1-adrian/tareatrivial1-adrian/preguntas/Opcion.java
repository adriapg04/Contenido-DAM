package preguntas;

public class Opcion {
    private String texto;
    private boolean esCorrecta;

    // Constructor que recibe el texto de la opcion y un boolean que indica si es correcta
    public Opcion(String texto, boolean esCorrecta) {
        this.texto = texto;
        this.esCorrecta = esCorrecta;
    }
    
    // Metodo getter para obtener el texto de la opcion
    public String getTexto() {
        return texto;
    }
    
    // Metodo getter para saber si esta opcion es correcta
    public boolean isEsCorrecta() {
        return esCorrecta;
    }
    
    // Metodo toString para devolver el texto de la opcion
    @Override
    public String toString() {
        return texto;
    }
}
