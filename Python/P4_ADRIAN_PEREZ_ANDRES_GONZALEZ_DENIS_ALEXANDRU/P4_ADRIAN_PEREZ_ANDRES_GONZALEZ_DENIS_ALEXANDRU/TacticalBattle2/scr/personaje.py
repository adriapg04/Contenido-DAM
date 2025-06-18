# Clase base para todos los personajes del juego
class Personaje:
    def __init__(self, vida_maxima, dano, posicion, equipo):
        self.vida_maxima = vida_maxima      # Cuanta vida total puede tener
        self.vida_actual  = vida_maxima     # Cuanta vida tiene en este momento
        self.dano         = dano            # Cuanto dano hace en un ataque normal
        self.posicion     = posicion        # En que casilla esta en el tablero (por ejemplo, "B2")
        self.enfriamiento = 0               # Cuantos turnos debe esperar para usar su habilidad otra vez
        self.equipo       = equipo          # Lista de personajes que estan en su mismo equipo

    def mover(self, nueva_posicion):
        """
        Cambia la posicion del personaje si la casilla no esta ocupada por un aliado
        """
        ocupadas = [p.posicion for p in self.equipo]  # Lista de casillas ya ocupadas por aliados
        if nueva_posicion in ocupadas:
            print("Error: la casilla ya esta ocupada por un aliado.")  # No se puede mover alli
        else:
            self.posicion = nueva_posicion  # Si esta libre, se mueve a esa casilla

    def puede_usar(self) -> bool:
        # Devuelve True si la habilidad esta lista para usarse (no esta en enfriamiento)
        return self.enfriamiento == 0

    def enfriar(self):
        # Activa el enfriamiento: se pone a 1, lo que indica que hay que esperar un turno
        self.enfriamiento = 1

    def bajar_enfriamiento(self):
        # Cada turno, si el enfriamiento esta activo, se reduce en 1
        if self.enfriamiento > 0:
            self.enfriamiento -= 1
