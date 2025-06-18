# Importamos las clases de cada tipo de personaje desde sus archivos
from personajes.medico import Medico
from personajes.inteligencia import Inteligencia
from personajes.artillero import Artillero
from personajes.francotirador import Francotirador

# Importamos funciones para validar las casillas del tablero
from utils import validar_celda, comprobar_celda_disponible

# Clase Jugador: representa a cada jugador del juego
class Jugador:
    def __init__(self, nombre):
        self.nombre   = nombre     # Nombre del jugador
        self.equipo   = []         # Lista con sus personajes
        self.oponente = None       # Otro jugador contra el que juega
        self.informe  = ""         # Informacion sobre el ultimo turno del rival

    def set_oponente(self, oponente):
        self.oponente = oponente   # Se guarda quien es el rival

    def crear_equipo(self):
        """Crea el equipo inicial con 4 personajes"""
        self.equipo = [
            Medico("A1", self.equipo),
            Inteligencia("B1", self.equipo),
            Artillero("B3", self.equipo),
            Francotirador("C4", self.equipo),
        ]

    def posicionar_equipo(self):
        """Pide al jugador colocar sus personajes en el tablero, sin repetir ni chocar con el rival"""
        usadas = []  # Lista de casillas ya usadas por este jugador
        for p in self.equipo:
            while True:
                pos = input(f"{self.nombre}, celda para {type(p).__name__}: ").upper()
                if not validar_celda(pos):
                    print("Celda invalida.")
                elif pos in usadas:
                    print("Celda ya usada por ti.")
                elif self.oponente and pos in [x.posicion for x in self.oponente.equipo]:
                    print("Celda ocupada por rival.")
                else:
                    p.posicion = pos
                    usadas.append(pos)
                    break

    def turno(self) -> bool:
        """Muestra el estado actual y ejecuta un turno. Devuelve True si gana"""
        print(f"\nInforme previo: {self.informe}\n")
        print("Estado equipo:")
        for p in self.equipo:
            if p.vida_actual > 0:
                print(f" {type(p).__name__} en {p.posicion} [Vida {p.vida_actual}]")

        codigo = self.realizar_accion()  # Jugador elige que hacer
        resultado = self.oponente.recibir_accion(codigo)  # El rival recibe el efecto

        if resultado.get("fin"):  # Si se termina la partida
            return True
        self.oponente.informe = resultado.get("mensaje", "")
        return False

    def realizar_accion(self) -> str:
        """Muestra opciones al jugador y devuelve el codigo de lo que hizo"""
        vivos = [p for p in self.equipo if p.vida_actual > 0]  # Solo personajes vivos
        for i, p in enumerate(vivos, 1):
            print(f"{i}: {type(p).__name__} en {p.posicion} (Vida {p.vida_actual})")

        sel = int(input("Selecciona personaje: ")) - 1
        p = vivos[sel]

        act = input("Mover (M) o Habilidad (H): ").upper()
        if act == 'M':
            dest = input("Celda destino: ").upper()
            p.mover(dest)
            return ''

        if isinstance(p, Medico):
            idx = int(input("A quien curar (numero): ")) - 1
            return f"M{idx}"  # Codigo para el medico

        else:
            cel = input("Celda objetivo: ").upper()
            inic = type(p).__name__[0]  # Primera letra del tipo (A, F, I)
            return f"{inic}{cel}"      # Ejemplo: A3B (Artillero, B3)

    def recibir_accion(self, codigo: str) -> dict:
        """
        Recibe una accion del rival, la aplica y devuelve el resultado.
        Devuelve un diccionario con un mensaje y si la partida termino.
        """
        if not codigo:
            return {"mensaje": "", "fin": False}

        inic = codigo[0]  # Primer caracter indica el tipo de accion

        # 1) Medico (M#): cura a un aliado
        if inic == 'M':
            idx = int(codigo[1:])
            objetivo = self.equipo[idx]
            objetivo.vida_actual = objetivo.vida_maxima
            return {"mensaje": f"Medico ha curado a {type(objetivo).__name__}.", "fin": False}

        # 2) Inteligencia (IXY): muestra enemigos en zona 2x2
        if inic == 'I':
            origen = codigo[1:].upper()
            fila, col = origen[0], int(origen[1])
            area = [
                f"{fila}{col}", f"{fila}{col + 1}",
                f"{chr(ord(fila) + 1)}{col}", f"{chr(ord(fila) + 1)}{col + 1}"
            ]
            visibles = [p.posicion for p in self.equipo if p.posicion in area]
            if visibles:
                return {"mensaje": "Enemigos avistados en " + ", ".join(visibles), "fin": False}
            else:
                return {"mensaje": "Ningun personaje ha sido revelado.", "fin": False}

        # 3) Artillero (AXY): hace dano en zona 2x2
        if inic == 'A':
            origen = codigo[1:].upper()
            fila, col = origen[0], int(origen[1])
            area = [
                f"{fila}{col}", f"{fila}{col + 1}",
                f"{chr(ord(fila) + 1)}{col}", f"{chr(ord(fila) + 1)}{col + 1}"
            ]
            mensaje = []
            for p in self.equipo[:]:
                if p.posicion in area:
                    p.vida_actual -= 1
                    if p.vida_actual <= 0:
                        mensaje.append(f"{type(p).__name__} eliminado en {p.posicion}.")
                        self.equipo.remove(p)
                    else:
                        mensaje.append(f"{type(p).__name__} herido en {p.posicion} [Vida {p.vida_actual}]")

            if not mensaje:
                mensaje = ["Ningun personaje ha sido herido."]
            # Si ya no quedan atacantes, se termina la partida
            fin = not any(isinstance(x, (Artillero, Francotirador)) for x in self.equipo)
            return {"mensaje": " ".join(mensaje), "fin": fin}

        # 4) Francotirador (FXY): dispara a una casilla concreta
        if inic == 'F':
            target = codigo[1:].upper()
            for p in self.equipo[:]:
                if p.posicion == target:
                    p.vida_actual -= 3
                    if p.vida_actual <= 0:
                        self.equipo.remove(p)
                        fin = not any(isinstance(x, (Artillero, Francotirador)) for x in self.equipo)
                        return {"mensaje": f"{type(p).__name__} eliminado en {target}.", "fin": fin}
                    else:
                        return {"mensaje": f"{type(p).__name__} herido en {target} [Vida {p.vida_actual}].", "fin": False}
            return {"mensaje": f"No se encontro ningun enemigo en {target}.", "fin": False}

        # Si el codigo recibido no se reconoce
        return {"mensaje": "", "fin": False}
