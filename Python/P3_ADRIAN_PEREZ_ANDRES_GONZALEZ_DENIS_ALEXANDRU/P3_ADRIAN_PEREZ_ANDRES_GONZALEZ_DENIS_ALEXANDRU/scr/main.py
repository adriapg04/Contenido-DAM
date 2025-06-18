import random
from utils import limpiar_terminal
from jugador import Jugador
def main():
    # muestra mensaje de bienvenida
    print("Bienvenidos a Tactical Battle\n")

    # crea las instancias de los dos jugadores
    j1 = Jugador("Jugador 1")
    j2 = Jugador("Jugador 2")

    # elige aleatoriamente quien colocara primero
    primero = random.choice([j1, j2])
    segundo = j2 if primero is j1 else j1
    # informa quien comienza las colocaciones
    print(f"{primero.nombre} comienza colocando sus fichas en el tablero.\n")

    # --- FASE DE COLOCACION JUGADOR 1 ---
    # crea los personajes y pide sus posiciones
    primero.crear_equipo()
    primero.posicionar_equipo()
    # espera a que el jugador confirme para limpiar
    input(f"{primero.nombre}, pulsa INTRO para terminar tu colocacion")
    # desplaza lo seleccionado para limpiar la pantalla
    limpiar_terminal()

    # enlaza ambos jugadores para que puedan atacarse
    j1.set_oponente(j2)
    j2.set_oponente(j1)

    # --- FASE DE COLOCACION JUGADOR 2 ---
    # anuncia la fase de posicion del segundo jugador
    print(f"\nAhora {segundo.nombre} coloca sus fichas.\n")
    # crea los personajes y pide sus posiciones
    segundo.crear_equipo()
    segundo.posicionar_equipo()
    # espera confirmacion y limpia la pantalla
    input(f"{segundo.nombre}, pulsa INTRO para terminar tu colocacion")
    limpiar_terminal()

    # --- BUCLE PRINCIPAL DE TURNOS ---
    while True:
        # turno del jugador que comienza
        print(f"\nTurno de {primero.nombre}")
        # ejecuta el turno y comprueba si pierde
        if primero.turno():
            print(f"***** {segundo.nombre} ha ganado la partida! *****")
            break

        # turno del segundo jugador
        print(f"\nTurno de {segundo.nombre}")
        if segundo.turno():
            print(f"***** {primero.nombre} ha ganado la partida! *****")
            break

# ejecuta main solo si este archivo es el programa principal
if __name__ == "__main__":
    main()
