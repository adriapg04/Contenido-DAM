# Importamos una funcion para limpiar la pantalla de la terminal
from utils import limpiar_terminal

# Importamos la clase Jugador, que representa a cada jugador del juego
from jugador import Jugador

# Funcion principal del juego
def main():
    print("Bienvenidos a Tactical Battle\n")  # Mensaje de bienvenida
    input("Turno del Jugador 1. Pulsa INTRO para comenzar")  # Esperamos que el jugador pulse INTRO

    # Creamos dos objetos Jugador, uno para cada jugador
    j1 = Jugador("Jugador 1")
    j2 = Jugador("Jugador 2")

    # Indicamos que cada jugador tiene como oponente al otro
    j1.set_oponente(j2)
    j2.set_oponente(j1)

    # --- FASE DE COLOCACION DEL JUGADOR 1 ---
    j1.crear_equipo()           # Se crean los personajes del jugador 1
    j1.posicionar_equipo()      # El jugador 1 coloca a sus personajes en el tablero
    input("Jugador 1, pulsa INTRO para terminar tu colocacion")  # Esperamos que confirme
    limpiar_terminal()          # Limpiamos la pantalla para que el jugador 2 no vea nada

    # --- FASE DE COLOCACION DEL JUGADOR 2 ---
    input("Turno del Jugador 2. Pulsa INTRO para comenzar")  # Avisamos al jugador 2
    j2.crear_equipo()           # Se crean los personajes del jugador 2
    j2.posicionar_equipo()      # El jugador 2 coloca a sus personajes en el tablero
    input("Jugador 2, pulsa INTRO para terminar tu colocacion")  # Esperamos confirmacion
    limpiar_terminal()          # Limpiamos la pantalla

    # --- COMIENZO DEL JUEGO ---
    final = False  # Variable para saber si la partida ha terminado

    while not final:  # Mientras nadie haya ganado...
        input("Turno del Jugador 1. Pulsa INTRO para comenzar")
        final = j1.turno()  # Jugador 1 juega su turno
        if final:
            print("***** El Jugador 1 ha ganado la partida! *****")
            break  # Si ha ganado, salimos del bucle

        input("Jugador 1, pulsa INTRO para terminar tu turno")
        limpiar_terminal()  # Limpiamos la pantalla para el siguiente jugador

        input("Turno del Jugador 2. Pulsa INTRO para comenzar")
        final = j2.turno()  # Jugador 2 juega su turno
        if final:
            print("***** El Jugador 2 ha ganado la partida! *****")
            break  # Si ha ganado, salimos del bucle

        input("Jugador 2, pulsa INTRO para terminar tu turno")
        limpiar_terminal()

# Esto se ejecuta solo si lanzamos el archivo directamente desde la terminal
if __name__ == "__main__":
    main()
