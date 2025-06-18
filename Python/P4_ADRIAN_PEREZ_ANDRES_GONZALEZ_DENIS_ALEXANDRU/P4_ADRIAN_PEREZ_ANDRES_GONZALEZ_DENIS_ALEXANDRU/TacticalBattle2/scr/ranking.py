# Esta clase representa una entrada del ranking (un nodo de la lista)
class NodoRank:
    def __init__(self, nombre, puntos):
        self.nombre = nombre       # Nombre del jugador
        self.puntos = puntos       # Puntuacion obtenida por ese jugador en una partida
        self.prev = None           # Enlace al nodo anterior en la lista
        self.next = None           # Enlace al nodo siguiente en la lista

# Esta clase deberia encargarse de gestionar todo el ranking de jugadores
# usando una lista doblemente enlazada ordenada de mayor a menor puntuacion
# Sin embargo, actualmente este codigo no funciona completamente como se espera
class Ranking:
    def __init__(self, fichero=None):
        self.cabeza = None  # Deberia apuntar al primer nodo del ranking
        if fichero:
            self._cargar(fichero)  # Intenta cargar el ranking desde un archivo de texto

    # Esta funcion deberia leer un archivo con puntuaciones anteriores y cargarlas en la lista
    # En caso de funcionar bien, insertaria cada linea del archivo como un nodo en orden correcto
    # Pero no hay verificacion de errores de formato ni control si hay lineas mal escritas
    def _cargar(self, fichero):
        try:
            with open(fichero, 'r') as f:
                for linea in f:
                    linea = linea.strip()
                    if ':' in linea:
                        nombre, pts = linea.split(':')
                        self.insertar(nombre, int(pts))  # Deberia insertar en orden correcto
        except FileNotFoundError:
            pass  # Si el archivo no existe, lo ignora

    # Esta funcion deberia insertar un nuevo nodo con nombre y puntos en el lugar correcto de la lista
    # El orden deberia ser de mayor a menor puntuacion
    # El problema es que no valida si ya existe el jugador ni actualiza puntuaciones repetidas
    def insertar(self, nombre, puntos):
        nuevo = NodoRank(nombre, puntos)

        # Si la lista esta vacia, simplemente colocamos el nuevo nodo como primero
        if not self.cabeza:
            self.cabeza = nuevo
            return

        actual = self.cabeza
        anterior = None

        # Este bucle intenta encontrar donde colocar el nuevo nodo segun su puntuacion
        # Pero no hay control si dos jugadores tienen el mismo nombre
        while actual and actual.puntos >= puntos:
            anterior = actual
            actual = actual.next

        if anterior is None:
            # Insertar al principio de la lista
            nuevo.next = self.cabeza
            self.cabeza.prev = nuevo
            self.cabeza = nuevo
        else:
            # Insertar entre nodos o al final
            nuevo.next = anterior.next
            nuevo.prev = anterior
            anterior.next = nuevo
            if nuevo.next:
                nuevo.next.prev = nuevo
        # En caso de funcionar correctamente, esto deberia mantener la lista ordenada
        # Pero no hay forma de evitar duplicados ni control de errores si algo falla

    # Esta funcion deberia devolver el ranking completo en forma de texto
    # Cada linea tiene el formato nombre:puntos
    def to_string(self):
        lineas = []
        actual = self.cabeza
        while actual:
            lineas.append(f"{actual.nombre}:{actual.puntos}")
            actual = actual.next
        return "\n".join(lineas)
        # Si funcionara correctamente, este texto se usaria para mostrar o guardar el ranking

    # Esta funcion deberia guardar el ranking actualizado en un archivo de texto
    # Sobrescribiendo el archivo anterior
    def guardar(self, fichero):
        with open(fichero, 'w') as f:
            f.write(self.to_string())
        # Esto funcionaria bien si to_string genera correctamente el contenido de la lista
