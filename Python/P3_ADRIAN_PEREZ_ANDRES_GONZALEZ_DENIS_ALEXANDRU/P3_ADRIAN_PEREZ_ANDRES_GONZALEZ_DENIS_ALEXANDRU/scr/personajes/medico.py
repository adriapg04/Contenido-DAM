# Importa la clase base Personaje de la que heredara Medico
from personaje import Personaje

class Medico(Personaje):
   # Constructor que inicializa un Medico con valores especificos
   def __init__(self, posicion, equipo):
       super().__init__(vida_maxima=1, daño=0, posicion=posicion, equipo=equipo)

   # Metodo que implementa la habilidad especial del Medico (restaurar vida a un personaje)
   def habilidad(self, personaje):
       # Comprueba si el personaje es del mismo equipo y necesita curacion
       if personaje in self.equipo and personaje.vida_actual < personaje.vida_maxima:
           personaje.vida_actual = personaje.vida_maxima
           print(f"{type(personaje).__name__} ha sido curado.")
       elif personaje.vida_actual == personaje.vida_maxima:
           print("El personaje ya tiene vida completa.")

   # Metodo adicional para mostrar estado de salud de todos los miembros del equipo
   def diagnosticar(self):
       for personaje in self.equipo:
           print(f"{type(personaje).__name__} - Vida restante: {personaje.vida_actual}/{personaje.vida_maxima}")