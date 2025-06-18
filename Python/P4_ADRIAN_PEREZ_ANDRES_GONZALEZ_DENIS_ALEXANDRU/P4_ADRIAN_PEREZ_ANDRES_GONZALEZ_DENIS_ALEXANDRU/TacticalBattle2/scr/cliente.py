# Importamos librerias necesarias
import socket         # Para poder comunicarse con el servidor por red
import sys            # Para leer los argumentos escritos al ejecutar el script
from jugador import Jugador  # Importamos la clase Jugador desde otro archivo

# Definimos la clase Cliente, que maneja la conexion con el servidor
class Cliente:
    def __init__(self, host, puerto, nombre):
        # Creamos el socket, que es el canal de comunicacion
        self.sock = socket.socket()
        self.sock.connect((host, puerto))  # Nos conectamos al servidor

        # Paso 1: Enviamos nuestro nombre al servidor
        self.sock.sendall(nombre.encode())

        # Paso 2: Recibimos el nombre del rival, viene en un mensaje tipo "OPP:Bob"
        opp_msg = self.sock.recv(1024).decode()
        rival = opp_msg.split(':', 1)[1]  # Nos quedamos con el nombre despues de "OPP:"
        print(f"Tu rival es {rival}")  # Mostramos el nombre del rival

        # Paso 3: Creamos nuestro objeto Jugador con nuestro nombre
        self.jugador = Jugador(nombre)
        self.jugador.crear_equipo()  # Creamos nuestro equipo de personajes

    def run(self):
        # Paso 1: Esperamos que el servidor nos diga si podemos colocar el equipo
        msg = self.sock.recv(1024).decode()
        if msg == "COMIENZA_COLOCACION":
            self.jugador.posicionar_equipo()  # Posicionamos a nuestros personajes
            for p in self.jugador.equipo:
                self.sock.sendall(p.posicion.encode())  # Enviamos la posicion de cada personaje

        # Paso 2: Esperamos el mensaje de inicio de partida
        msg = self.sock.recv(1024).decode()
        if msg == "INICIO_PARTIDA":
            print("Comienza la partida!")  # Avisamos al jugador

        # Paso 3: Empezamos el bucle de turnos, que continua hasta que alguien gane o pierda
        while True:
            msg = self.sock.recv(1024).decode()  # Esperamos mensaje del servidor

            if msg == "TURNO":
                # Es nuestro turno
                codigo = self.jugador.realizar_accion().encode()  # Hacemos una accion y la codificamos
                self.sock.sendall(codigo)  # Enviamos el codigo al servidor

                resultado = self.sock.recv(1024).decode()  # Recibimos el resultado
                print(resultado)  # Mostramos lo que paso

                if resultado.startswith("FIN"):  # Si el mensaje empieza con "FIN", ganamos
                    print("Has ganado!")
                    break  # Salimos del bucle

            elif msg.startswith("ACCION:"):
                # Es el turno del rival, recibimos su jugada
                codigo_rival = msg.split(":", 1)[1]  # Sacamos el codigo de la jugada
                res = self.jugador.recibir_accion(codigo_rival)  # Calculamos que efecto tiene

                texto = res["mensaje"]  # El mensaje que describe lo que paso

                if res["fin"]:
                    texto = "FIN"  # Si la partida termina, lo avisamos
                self.sock.sendall(texto.encode())  # Enviamos el resultado al servidor

                if res["fin"]:
                    print("Has perdido!")  # Si perdemos, lo avisamos
                    break  # Salimos del bucle

# Este bloque se ejecuta solo si el archivo se corre directamente desde la consola
if __name__ == '__main__':
    # Leemos los datos desde la linea de comandos: ip, puerto y nombre de jugador
    host, puerto, nombre = sys.argv[1], int(sys.argv[2]), sys.argv[3]
    # Creamos el cliente y arrancamos el juego
    Cliente(host, puerto, nombre).run()
