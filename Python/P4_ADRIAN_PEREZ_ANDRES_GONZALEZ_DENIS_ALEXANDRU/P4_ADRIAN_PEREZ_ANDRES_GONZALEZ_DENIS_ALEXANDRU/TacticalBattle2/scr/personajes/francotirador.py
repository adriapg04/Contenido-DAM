# Importamos la clase Personaje desde el archivo personaje.py
from personaje import Personaje

# Creamos la clase Francotirador, que es un tipo de personaje con ataque preciso
class Francotirador(Personaje):
    def __init__(self, posicion, equipo):
        # Tiene 3 puntos de vida y hace 3 puntos de dano
        super().__init__(vida_maxima=3, dano=3, posicion=posicion, equipo=equipo)

    # Esta es la habilidad especial del Francotirador
    def habilidad(self, aliados, enemigos):
        """
        Dispara a una sola casilla y elimina al enemigo si esta ahi
        """
        # Si no puede usar la habilidad todavia, se muestra un mensaje
        if not self.puede_usar():
            print("Habilidad en enfriamiento.")
            return

        # Se pide al jugador que diga en que casilla quiere disparar
        target = input("Celda a disparar (p.ej. C3): ").upper()

        # Se revisan todos los enemigos
        for e in enemigos[:]:
            if e.posicion == target:
                print(f"{type(e).__name__} eliminado en {target}.")
                enemigos.remove(e)  # Se elimina el enemigo si esta en esa casilla
                break
        else:
            # Si no habia nadie en esa casilla
            print(f"No se encontro enemigo en {target}.")

        # Se activa el enfriamiento de la habilidad
        self.enfriar()
