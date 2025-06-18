# Importamos la clase general Personaje desde el archivo personaje.py
from personaje import Personaje

# Creamos la clase Medico, que es un tipo especial de personaje que no ataca, solo cura
class Medico(Personaje):
    def __init__(self, posicion, equipo):
        # Este personaje tiene solo 1 punto de vida (es muy debil) y no hace dano (dano = 0)
        # Se guarda su posicion y su equipo.
        super().__init__(vida_maxima=1, dano=0, posicion=posicion, equipo=equipo)

    # Esta es la habilidad especial del Medico
    def habilidad(self, aliados, enemigos=None):
        """
        Esta habilidad busca al primer companero que no tenga la vida completa
        y lo cura completamente (le devuelve toda la vida).
        """
        # Si la habilidad esta en enfriamiento (es decir, ya se uso antes y no se ha recargado), no se puede usar
        if not self.puede_usar():
            print("Habilidad en enfriamiento.")  # Se avisa al jugador
            return  # Salimos sin hacer nada

        # Empezamos sin saber a quien vamos a curar
        target = None

        # Recorremos a todos los aliados para encontrar el primero que no tenga toda la vida
        for a in aliados:
            if a.vida_actual < a.vida_maxima:
                target = a  # Guardamos al aliado que necesita curacion
                break       # Paramos el bucle porque ya encontramos uno

        # Si encontramos a alguien que necesita curacion...
        if target:
            target.vida_actual = target.vida_maxima  # Le devolvemos toda la vida
            print(f"{type(target).__name__} ha sido curado.")  # Avisamos al jugador
        else:
            # Si todos los aliados estan con la vida completa, no se cura a nadie
            print("Nadie necesita curacion.")

        # Activamos el enfriamiento para que no se pueda usar la habilidad otra vez enseguida
        self.enfriar()
