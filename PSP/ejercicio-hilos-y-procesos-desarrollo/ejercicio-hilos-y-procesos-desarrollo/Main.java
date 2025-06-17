package programacion;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Estacionamiento estacionamiento = new Estacionamiento(10);
        List<Coche> coches = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            boolean vip = i % 3 == 0;
            coches.add(new Coche("Coche " + i, vip, estacionamiento));
        }

        for (Coche coche : coches) {
            coche.start();
        }

        for (Coche coche : coches) {
            coche.join();
        }

        System.out.println("Lista de coches aparcados:");
        for (Coche coche : coches) {
            System.out.println(coche);
        }
    }
}
