package programacion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {
    private final Semaphore semaforo;
    private final List<Coche> cochesAparcados;

    public Estacionamiento(int plazas) {
        this.semaforo = new Semaphore(plazas);
        this.cochesAparcados = new ArrayList<>();
    }

    public boolean entrar(Coche coche) throws InterruptedException {
        if (coche.esVip()) {
            synchronized (this) {
                if (semaforo.tryAcquire()) {
                    cochesAparcados.add(coche);
                    return true;
                } else {
                    desalojarCocheNormal();
                    semaforo.acquire();
                    cochesAparcados.add(coche);
                    return true;
                }
            }
        } else {
            synchronized (this) {
                if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
                    cochesAparcados.add(coche);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public void salir(Coche coche) {
        synchronized (this) {
            cochesAparcados.remove(coche);
            semaforo.release();
        }
    }

    private void desalojarCocheNormal() {
        synchronized (this) {
            for (Coche coche : cochesAparcados) {
                if (!coche.esVip()) {
                    cochesAparcados.remove(coche);
                    semaforo.release();
                    break;
                }
            }
        }
    }
}
