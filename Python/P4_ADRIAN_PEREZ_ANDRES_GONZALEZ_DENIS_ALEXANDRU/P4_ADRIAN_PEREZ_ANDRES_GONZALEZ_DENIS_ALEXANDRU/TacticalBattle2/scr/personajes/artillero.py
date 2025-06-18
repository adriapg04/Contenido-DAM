# Importamos la clase general Personaje desde el archivo personaje.py
from personaje import Personaje

# Creamos la clase Artillero, que es un tipo especial de personaje
class Artillero(Personaje):
    def __init__(self, posicion, equipo):
        # Tiene 2 puntos de vida y hace 1 punto de dano por ataque
        # Tambien se guarda su posicion y a que equipo pertenece
        super().__init__(vida_maxima=2, dano=1, posicion=posicion, equipo=equipo)

    # Esta es la habilidad especial del Artillero
    def habilidad(self, aliados, enemigos):
        """
        Dispara a un area de 2x2 casillas y hace 1 punto de dano a cada enemigo que este dentro
        """
        # Si la habilidad no esta disponible todavia, se muestra un mensaje y se sale
        if not self.puede_usar():
            print("Habilidad en enfriamiento.")
            return

        # Se pide al jugador que escriba una casilla desde donde se inicia el ataque (por ejemplo, B2)
        origen = input("Celda esquina sup izqda (p.ej. B2): ").upper()

        # Se separa la letra (fila) y el numero (columna)
        fila, col = origen[0], int(origen[1])

        # Se crean las 4 casillas que forman el area del ataque (2x2)
        area = [
            f"{fila}{col}",
            f"{fila}{col+1}",
            f"{chr(ord(fila)+1)}{col}",
            f"{chr(ord(fila)+1)}{col+1}"
        ]

        # Se revisan todos los enemigos para ver si alguno esta en esa area
        for e in enemigos[:]:
            if e.posicion in area:
                e.vida_actual -= 1  # Se le quita 1 punto de vida
                if e.vida_actual <= 0:
                    print(f"{type(e).__name__} eliminado en {e.posicion}.")
                    enemigos.remove(e)  # Se elimina si muere
                else:
                    print(f"{type(e).__name__} herido en {e.posicion} [Vida {e.vida_actual}]")

        # Se activa el enfriamiento para que no se pueda usar otra vez de inmediato
        self.enfriar()
