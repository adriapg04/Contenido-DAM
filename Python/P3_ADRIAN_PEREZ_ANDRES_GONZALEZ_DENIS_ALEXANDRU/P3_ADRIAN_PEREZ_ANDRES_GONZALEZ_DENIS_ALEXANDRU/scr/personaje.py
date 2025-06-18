# Clase base para todos los personajes del juego
class Personaje:
    # Inicializa vida maxima, daño, posicion y lista de equipo
    def __init__(self, vida_maxima, daño, posicion, equipo):
        self.vida_maxima = vida_maxima
        self.vida_actual = vida_maxima
        self.daño = daño
        self.posicion = posicion
        self.enfriamiento_restante = 0
        self.equipo = equipo

    # Intenta mover al personaje a nueva_posicion si no hay un aliado ahi
    def mover(self, nueva_posicion):
        posiciones_ocupadas = [p.posicion for p in self.equipo]
        if nueva_posicion in posiciones_ocupadas:
            print("Error: La celda ya esta ocupada por un aliado.")
        else:
            self.posicion = nueva_posicion

    # Gestiona el enfriamiento y prepara el uso de la habilidad
    def habilidad(self):
        if self.enfriamiento_restante > 0:
            print(f"La habilidad de {type(self).__name__} esta en enfriamiento. Restan {self.enfriamiento_restante} turnos.")
            return None
        self.enfriamiento_restante = 1
        return None
