package preguntas;

import java.util.ArrayList;
import java.util.Collections;

// La clase Pregunta representa una pregunta del trivial
public class Pregunta {
    private String enunciado;
    private ArrayList<Opcion> opciones;

    // Constructor que recibe el enunciado y la lista de opciones
    public Pregunta(String enunciado, ArrayList<Opcion> opciones) {
        this.enunciado = enunciado;
        this.opciones = opciones;
    }
    
    // getter para obtener el enunciado de la pregunta
    public String getEnunciado() {
        return enunciado;
    }
    
    // getter para obtener la lista de opciones
    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }
    
    // Metodo para mezclar aleatoriamente el orden de las opciones
    public void mezclarOpciones() {
        Collections.shuffle(opciones); // Utiliza el metodo shuffle para desordenar la lista
    }
    
    // Metodo toString que devuelve la pregunta y sus opciones formateadas
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Crea un StringBuilder para construir la cadena
        sb.append(enunciado + "\n"); // Añade el enunciado y un salto de línea
        char letra = 'A'; // Variable para etiquetar las opciones (A, B, C, D)
        // Recorre cada opcion en la lista
        for(Opcion op : opciones) {
            // Añade la letra, la opcion y un salto de linea al StringBuilder
            sb.append(letra + ") " + op.getTexto() + "\n");
            letra++; // Incrementa la letra para la siguiente opción
        }
        return sb.toString(); // Devuelve la cadena completa
    }
}
