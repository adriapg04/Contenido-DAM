# Importa las clases de cada tipo de personaje
from personajes.medico import Medico
from personajes.inteligencia import Inteligencia
from personajes.artillero import Artillero
from personajes.francotirador import Francotirador

class Jugador:
    # Inicializa el jugador con nombre, equipo vacio, sin oponente y sin informe
    def __init__(self, nombre):
        self.nombre = nombre
        self.equipo = []
        self.oponente = None
        self.informe = ""

    # Guarda la referencia al rival para poder atacarlo o informarlo
    def set_oponente(self, oponente):
        self.oponente = oponente

    # Crea los cuatro personajes del equipo y los añade a la lista
    def crear_equipo(self):
        self.equipo.append(Medico("A1", self.equipo))
        self.equipo.append(Inteligencia("B1", self.equipo))
        self.equipo.append(Artillero("B3", self.equipo))
        self.equipo.append(Francotirador("C4", self.equipo))

    # Permite al jugador asignar una celda valida a cada personaje
    def posicionar_equipo(self):
        # Elimina las posiciones por defecto para empezar de cero
        for p in self.equipo:
            p.posicion = None

        # Lista de celdas ya asignadas a este jugador
        used = []

        for personaje in self.equipo:
            while True:
                # Muestra el prompt con el nombre del jugador y tipo de personaje
                prompt = f"{self.nombre}, indica la celda para {type(personaje).__name__}: "
                pos = input(prompt)

                # Verifica que la celda este entre A1 y D4
                if pos not in [f"{f}{c}" for f in "ABCD" for c in "1234"]:
                    print("Celda invalida. Debe ser A1–D4.")
                    continue

                # Verifica que la celda no este repetida en este equipo
                if pos in used:
                    print("La celda ya esta ocupada por tu equipo.")
                    continue

                # Verifica que la celda no este ocupada por el rival
                if self.oponente and pos in [p.posicion for p in self.oponente.equipo]:
                    print("La celda ya esta ocupada por el rival.")
                    continue

                # Asigna la posicion al personaje y la marca como usada
                personaje.posicion = pos
                used.append(pos)
                break

    # Gestiona un turno completo y devuelve True si el jugador pierde
    def turno(self) -> bool:
        # Muestra lo que hizo el rival en su ultimo turno
        print(f"\nInforme del turno anterior: {self.informe}\n")

        # Muestra el estado actual del equipo (solo los vivos)
        print("Estado del equipo:")
        for p in self.equipo:
            if p.vida_actual > 0:
                print(f"{type(p).__name__} esta en {p.posicion} "
                      f"[Vida {p.vida_actual}/{p.vida_maxima}]")

        # Comprueba si quedan unidades militares vivas para seguir jugando
        militares = [
            p for p in self.equipo
            if isinstance(p, (Artillero, Francotirador)) and p.vida_actual > 0
        ]
        if not militares:
            print(f"***** {self.nombre} ha perdido la partida! *****")
            return True

        # Muestra el menu de personajes disponibles para actuar
        print("\nOpciones disponibles:")
        for i, p in enumerate(self.equipo, start=1):
            if p.vida_actual > 0:
                print(f"{i}: {type(p).__name__} en {p.posicion} "
                      f"(Vida {p.vida_actual}/{p.vida_maxima})")

        # Bucle para validar la seleccion de personaje
        while True:
            try:
                sel = int(input("Selecciona un personaje (numero): ")) - 1
                if 0 <= sel < len(self.equipo) and self.equipo[sel].vida_actual > 0:
                    break
                print("Numero fuera de rango o personaje muerto.")
            except ValueError:
                print("Debes introducir un numero.")
        elegido = self.equipo[sel]

        # Pide si mover o usar habilidad dependiendo del tipo de personaje
        accion = input(
            f"¿Mover (M) o Usar habilidad (H) con {type(elegido).__name__}? "
        ).upper()
        if accion == "M":
            # Movimiento: pide la celda destino y valida su formato
            nueva = input("Indica la celda destino: ")
            if nueva in [f"{f}{c}" for f in "ABCD" for c in "1234"]:
                elegido.mover(nueva)
            else:
                print("Posicion invalida. No se realizo el movimiento.")

        elif accion == "H":
            # Habilidad de area o disparo: actua sobre el equipo rival
            if isinstance(elegido, (Artillero, Inteligencia, Francotirador)):
                obj = input("Indica la posicion objetivo: ")
                elegido.habilidad(self.oponente.equipo, obj)
            # Habilidad del medico: cura a un aliado seleccionado
            elif isinstance(elegido, Medico):
                print("¿A quien quieres curar?")
                for idx, p in enumerate(self.equipo, start=1):
                    print(f"{idx}: {type(p).__name__} en {p.posicion} "
                          f"[{p.vida_actual}/{p.vida_maxima}]")
                sel2 = int(input("Selecciona numero: ")) - 1
                elegido.habilidad(self.equipo[sel2])

        # Almacena en el rival una descripcion de la accion realizada
        self.oponente.informe = f"{type(elegido).__name__} realizo una accion."
        return False
