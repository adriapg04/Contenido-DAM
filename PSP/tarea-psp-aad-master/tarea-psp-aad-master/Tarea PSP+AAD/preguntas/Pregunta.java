package preguntas;

import java.util.ArrayList;
import java.util.Collections; // Importa la clase Collections para mezclar listas


public class Pregunta {

    private String enunciado;
    // Lista que guarda las opciones de respuesta para la pregunta
    private ArrayList<Opcion> opciones;


    // Recibe el texto de la pregunta y una lista con las opciones de respuesta
    public Pregunta(String enunciado, ArrayList<Opcion> opciones) {
        this.enunciado = enunciado; // Guarda el texto en la variable "enunciado"
        this.opciones = opciones;   // Guarda la lista de opciones en la variable "opciones"
    }

    // Metodo para obtener el texto de la pregunta
    public String getEnunciado() {
        return enunciado; // Devuelve el enunciado de la pregunta
    }

    // Metodo para obtener la lista de opciones de respuesta
    public ArrayList<Opcion> getOpciones() {
        return opciones; // Devuelve la lista de opciones
    }

    // Metodo para mezclar el orden de las opciones de forma aleatoria
    public void mezclarOpciones() {
        Collections.shuffle(opciones); // Mezcla la lista de opciones para que el orden sea aleatorio
    }

    // Metodo toString que convierte la pregunta y sus opciones en un texto formateado
    @Override
    public String toString() {
        // Crea un objeto StringBuilder para ir construyendo el texto de la pregunta
        StringBuilder sb = new StringBuilder();
        // Agrega el enunciado seguido de un salto de linea
        sb.append(enunciado + "\n");
        // Define una variable letra que se usara para etiquetar cada opcion (A, B, C... )
        char letra = 'A';
        // Recorre cada opcion en la lista de respuestas
        for(Opcion op : opciones) {
            // Agrega la etiqueta (letra), un parentesis, el texto de la opcion y un salto de linea
            sb.append(letra + ") " + op.getTexto() + "\n");
            letra++; // Incrementa la letra para la siguiente opcion
        }
        // Devuelve el texto completo que contiene la pregunta y sus opciones
        return sb.toString();
    }
}
