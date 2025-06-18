# Importa la clase base Personaje de la que heredara Francotirador
from personaje import Personaje

class Francotirador(Personaje):
   # Constructor que inicializa un Francotirador con valores especificos
   def __init__(self, posicion, equipo):
       super().__init__(vida_maxima=3, daño=3, posicion=posicion, equipo=equipo)

   # Metodo que implementa la habilidad especial del Francotirador (disparo preciso a una celda)
   def habilidad(self, tablero, objetivo):
       # Buscar si hay algun enemigo en la celda objetivo
       objetivo_enemigo = next((p for p in tablero if p.posicion == objetivo), None)

       # Si no hay enemigo en el objetivo
       if not objetivo_enemigo:
           print(f"No se encontró ningún enemigo en la posición {objetivo}.")
           return None

       # Si hay enemigo, aplicar el daño
       objetivo_enemigo.vida_actual -= self.danyo
       if objetivo_enemigo.vida_actual <= 0:
           print(f"{type(objetivo_enemigo).__name__} ha sido eliminado en {objetivo}.")
           tablero.remove(objetivo_enemigo)  # Elimina al personaje si su vida llega a 0
       else:
           print(f"{type(objetivo_enemigo).__name__} ha sido herido en {objetivo} [Vida restante: {objetivo_enemigo.vida_actual}]")