# Importa la clase base Personaje de la que heredara Artillero
from personaje import Personaje

class Artillero(Personaje):
   # Constructor que inicializa un Artillero con valores especificos
   def __init__(self, posicion, equipo):
       super().__init__(vida_maxima=2, daño=1, posicion=posicion, equipo=equipo)

   # Metodo que implementa la habilidad especial del Artillero (disparo en area 2x2)
   def habilidad(self, tablero, posicion):
       # Encuentra enemigos que esten dentro del area de efecto
       enemigos_afectados = [p for p in tablero if p.posicion in self.obtener_area(posicion)]
       if enemigos_afectados:
           # Para cada enemigo en el area, reduce su vida y comprueba si ha sido eliminado
           for enemigo in enemigos_afectados:
               enemigo.vida_actual -= self.daño
               if enemigo.vida_actual <= 0:
                   print(f"{type(enemigo).__name__} ha sido eliminado en {enemigo.posicion}.")
                   tablero.remove(enemigo)  # Quitamos al enemigo eliminado del tablero
               else:
                   print(f"{type(enemigo).__name__} ha sido herido en {enemigo.posicion} [Vida restante: {enemigo.vida_actual}]")
       else:
           print("Ningún personaje ha sido herido.")

   # Metodo alternativo para mostrar efectos visuales del disparo (no usado en el juego)
   def disparo_explosivo(self, tablero, posicion):
       area = self.obtener_area(posicion)
       for celda in area:
           print(f"¡Explosión en {celda}!")

   # Calcula las 4 celdas que forman el area 2x2 a partir de la esquina superior izquierda
   def obtener_area(self, posicion):
       fila, columna = posicion[0], int(posicion[1])
       return [f"{fila}{columna}", f"{fila}{columna+1}", f"{chr(ord(fila)+1)}{columna}", f"{chr(ord(fila)+1)}{columna+1}"]