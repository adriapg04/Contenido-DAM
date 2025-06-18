# Importamos librerias necesarias
import socket              # Para comunicacion por red
import threading           # Para manejar varios jugadores a la vez
from cola import Cola      # Cola para la sala de espera de jugadores
from ranking import Ranking  # Para guardar y manejar puntuaciones

# Clase principal del servidor del juego
class Servidor:
    def __init__(self, puerto, max_partidas, fichero_rank):
        self.puerto = puerto                         # Numero de puerto donde escuchara el servidor
        self.max_partidas = max_partidas             # Cuantas partidas pueden estar activas al mismo tiempo
        self.ranking = Ranking(fichero_rank)         # Objeto para gestionar el ranking de jugadores
        self.fichero_rank = fichero_rank             # Ruta al archivo de texto del ranking
        self.lobby = Cola()                          # Cola de espera para los jugadores
        self.lock = threading.Lock()                 # Bloqueo para acceder a recursos compartidos sin errores
        self.partidas_activas = 0                    # Cuantas partidas hay en juego actualmente

    def start(self):
        # Creamos y configuramos el socket del servidor
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('', self.puerto))                 # Acepta conexiones desde cualquier direccion IP
        sock.listen()                                # Se pone en modo "escuchando"
        print(f"Servidor escuchando en puerto {self.puerto}")

        # Bucle principal que acepta nuevos clientes
        while True:
            conn, addr = sock.accept()               # Acepta una nueva conexion
            # Crea un hilo para atender al cliente sin bloquear a los demas
            threading.Thread(target=self.handle_cliente, args=(conn,)).start()

    def handle_cliente(self, conn):
        # Recibe el nombre del jugador
        nombre = conn.recv(1024).decode().strip()

        # Accedemos a la cola con bloqueo para evitar conflictos entre hilos
        with self.lock:
            self.lobby.enqueue((nombre, conn))       # Metemos al jugador en la cola
            self.try_emparejar()                     # Intentamos emparejar si hay suficientes jugadores

    def try_emparejar(self):
        # Mientras haya al menos 2 jugadores esperando y espacio para mas partidas...
        while len(self.lobby) >= 2 and self.partidas_activas < self.max_partidas:
            # Sacamos a los dos primeros jugadores
            n1, c1 = self.lobby.dequeue()
            n2, c2 = self.lobby.dequeue()
            self.partidas_activas += 1               # Aumentamos el contador de partidas
            # Lanzamos un hilo para gestionar la nueva partida
            threading.Thread(target=self.partida, args=(n1, c1, n2, c2)).start()

    def partida(self, n1, c1, n2, c2):
        # --- FASE 0: Informamos a los jugadores del nombre de su oponente ---
        c1.sendall(f"OPP:{n2}".encode())
        c2.sendall(f"OPP:{n1}".encode())

        # --- FASE 1: Fase de colocacion ---
        for sock in (c1, c2):
            sock.sendall(b"COMIENZA_COLOCACION")  # Indicamos que deben colocar su equipo

        # Recibimos 4 posiciones del jugador 1
        for _ in range(4):
            c1.recv(1024)
        # Recibimos 4 posiciones del jugador 2
        for _ in range(4):
            c2.recv(1024)

        # --- FASE 2: Inicio de partida ---
        for sock in (c1, c2):
            sock.sendall(b"INICIO_PARTIDA")  # Enviamos mensaje de que empieza el juego

        # --- FASE 3: Turnos alternos ---
        turno = 0
        while True:
            # Se alternan los jugadores: primero uno, luego el otro
            activo, pasivo = (c1, c2) if turno % 2 == 0 else (c2, c1)

            # a) Le decimos al jugador activo que es su turno
            activo.sendall(b"TURNO")

            # b) Recibimos la accion del jugador activo (por ejemplo "A3" o "M2")
            codigo = activo.recv(1024)

            # c) Le pasamos esa accion al jugador pasivo (el que recibe el ataque)
            pasivo.sendall(b"ACCION:" + codigo)

            # d) El jugador pasivo responde con el resultado del ataque
            resultado = pasivo.recv(1024)

            # e) Le enviamos ese resultado de vuelta al jugador activo
            activo.sendall(resultado)

            # f) Si el resultado empieza por "FIN", se termina la partida
            if resultado.startswith(b"FIN"):
                break

            turno += 1  # Pasamos al siguiente turno

        # Cuando termina la partida, restamos 1 al numero de partidas activas
        self.partidas_activas -= 1

# Este bloque se ejecuta si el archivo se lanza directamente desde la terminal
if __name__ == '__main__':
    import sys
    puerto  = int(sys.argv[1])     # Primer argumento: numero de puerto
    max_p   = int(sys.argv[2])     # Segundo argumento: maximo de partidas simultaneas
    fichero = sys.argv[3]          # Tercer argumento: archivo del ranking
    Servidor(puerto, max_p, fichero).start()  # Arrancamos el servidor
