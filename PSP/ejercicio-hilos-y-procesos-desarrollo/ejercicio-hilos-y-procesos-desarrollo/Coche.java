package programacion;

public class Coche extends Thread {
    private final String nombre;
    private final boolean vip;
    private final Estacionamiento estacionamiento;

    public Coche(String nombre, boolean vip, Estacionamiento estacionamiento) {
        this.nombre = nombre;
        this.vip = vip;
        this.estacionamiento = estacionamiento;
    }

    public boolean esVip() {
        return vip;
    }

    @Override
    public void run() {
        try {
            if (estacionamiento.entrar(this)) {
                System.out.println(this + " ha aparcado.");
                Thread.sleep((long) (2000 + Math.random() * 4000));
                estacionamiento.salir(this);
                System.out.println(this + " ha salido.");
            } else {
                System.out.println(this + " no encontró plaza.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return (vip ? "[VIP] " : "[Normal] ") + nombre;
    }
}
