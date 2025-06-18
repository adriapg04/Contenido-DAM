# Importamos la clase Personaje desde el archivo personaje.py
from personaje import Personaje

# Creamos la clase Inteligencia, que no hace dano pero ayuda viendo enemigos
class Inteligencia(Personaje):
    def __init__(self, posicion, equipo):
        # Tiene 2 puntos de vida y no hace dano
        super().__init__(vida_maxima=2, dano=0, posicion=posicion, equipo=equipo)

    # Habilidad especial para descubrir enemigos en una zona
    def habilidad(self, aliados, enemigos):
        """
        Muestra si hay enemigos en un area de 2x2 casillas
        """
        # Si no se puede usar la habilidad todavia, se muestra aviso
        if not self.puede_usar():
            print("Habilidad en enfriamiento.")
            return

        # Se pide al jugador que escriba la casilla desde donde mirar
        origen = input("Celda esquina sup izqda (p.ej. B2): ").upper()

        # Se separa la fila (letra) y la columna (numero)
        fila, col = origen[0], int(origen[1])

        # Se forma el area de 2x2 a partir de la casilla dada
        area = [
            f"{fila}{col}",
            f"{fila}{col+1}",
            f"{chr(ord(fila)+1)}{col}",
            f"{chr(ord(fila)+1)}{col+1}"
        ]

        # Se busca si hay enemigos en esa area
        encontrados = [e.posicion for e in enemigos if e.posicion in area]

        # Si se encuentran enemigos, se muestran sus posiciones
        if encontrados:
            print("Enemigos en:", ", ".join(encontrados))
        else:
            print("Ningun personaje revelado.")

        # Se activa el enfriamiento de la habilidad
        self.enfriar()
