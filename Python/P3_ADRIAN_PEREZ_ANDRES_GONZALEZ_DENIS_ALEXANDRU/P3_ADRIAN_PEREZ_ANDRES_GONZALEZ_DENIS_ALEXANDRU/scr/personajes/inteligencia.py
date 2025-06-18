# Importa la clase base Personaje de la que heredara Inteligencia
from personaje import Personaje

class Inteligencia(Personaje):
   # Constructor que inicializa un personaje de Inteligencia con valores especificos
   def __init__(self, posicion, equipo):
       super().__init__(vida_maxima=2, daño=0, posicion=posicion, equipo=equipo)

   # Metodo que implementa la habilidad especial de Inteligencia (revelar enemigos en area 2x2)
   def habilidad(self, tablero, posicion):
       # Busca enemigos dentro del area de exploracion
       enemigos_detectados = [p for p in tablero if p.posicion in self.obtener_area(posicion)]
       if enemigos_detectados:
           print(f"Enemigos avistados en {', '.join([p.posicion for p in enemigos_detectados])}")
       else:
           print("Ningún personaje ha sido revelado.")

   # Metodo adicional para marcar zonas como peligrosas (no usado en el juego principal)
   def marcar_peligro(self, tablero, posicion):
       area = self.obtener_area(posicion)
       print(f"Zona marcada como peligrosa: {', '.join(area)}")

   # Calcula las 4 celdas que forman el area 2x2 a partir de la esquina superior izquierda
   def obtener_area(self, posicion):
       fila, columna = posicion[0], int(posicion[1])
       return [f"{fila}{columna}", f"{fila}{columna+1}", f"{chr(ord(fila)+1)}{columna}", f"{chr(ord(fila)+1)}{columna+1}"]
