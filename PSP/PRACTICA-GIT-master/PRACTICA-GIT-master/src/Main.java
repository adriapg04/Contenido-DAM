import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean ejecutando = true;
/**
 * menu de opciones correcto
 */


/**
 * while para mantener la ejecucion salida cuando no inserten los datos
 */
        while (ejecutando) {
         System.out.println("\n--- Menú de Opciones ---");
            System.out.println("1. Recolectar datos");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    recolectarDatos(scanner);
                    break;
                case 2:
                    System.out.println("Saliendo del programa...");
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();

        
    }


    public static void recolectarDatos(Scanner scanner) {
        System.out.print("Ingrese la cantidad de datos a recolectar: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        String[] datos = new String[cantidad];
/**
 * for para recoleccion nde datos
 */
        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el dato #" + (i + 1) + ": ");
            datos[i] = scanner.nextLine();
        }

        System.out.println("\nDatos recolectados:");
        for (String dato : datos) {
            System.out.println("- " + dato);
        }


        if (cantidad <= 0) {
            System.out.println("Error: No se ingresaron datos válidos.");
        } else if (datos[0].isEmpty()) {
            System.out.println("Error: El primer dato no puede estar vacío.");
        } else {
            System.out.println("Datos ingresados correctamente.");
        }
    }
}
