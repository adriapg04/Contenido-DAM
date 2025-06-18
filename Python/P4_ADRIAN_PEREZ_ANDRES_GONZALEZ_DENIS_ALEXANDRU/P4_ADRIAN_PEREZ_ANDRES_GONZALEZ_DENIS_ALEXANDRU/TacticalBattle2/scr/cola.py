# Creamos una clase llamada Nodo, que representa un elemento de la cola
class Nodo:
    def __init__(self, dato):
        self.dato = dato           # Guarda el valor que queremos almacenar
        self.siguiente = None      # Apunta al siguiente nodo (al principio no hay ninguno)

# Creamos una clase llamada Cola, que es una estructura para guardar elementos en orden
class Cola:
    def __init__(self):
        self.frente = None         # El primer elemento de la cola (el que sale primero)
        self.final = None          # El ultimo elemento de la cola (el ultimo que entra)
        self.tamanio = 0           # Numero de elementos que hay en la cola

    # Este metodo nos dice si la cola esta vacia
    def is_empty(self):
        return self.tamanio == 0

    # Este metodo sirve para meter un nuevo elemento al final de la cola
    def enqueue(self, dato):
        nodo = Nodo(dato)          # Creamos un nuevo nodo con el dato que queremos guardar
        if self.final:             # Si ya hay elementos en la cola...
            self.final.siguiente = nodo  # El ultimo apunta al nuevo
        self.final = nodo          # Ahora el nuevo es el ultimo
        if not self.frente:        # Si la cola estaba vacia, el nuevo es tambien el primero
            self.frente = nodo
        self.tamanio += 1          # Aumentamos el numero de elementos en la cola

    # Este metodo sirve para sacar el primer elemento de la cola
    def dequeue(self):
        if self.is_empty():        # Si la cola esta vacia, no se puede sacar nada
            return None
        dato = self.frente.dato    # Guardamos el valor del primer elemento
        self.frente = self.frente.siguiente  # El segundo pasa a ser el primero
        if not self.frente:        # Si ya no queda ningun elemento, la cola queda vacia
            self.final = None
        self.tamanio -= 1          # Reducimos el tamanio de la cola
        return dato                # Devolvemos el valor que se saco

    # Este metodo permite ver el primer elemento sin sacarlo
    def peek(self):
        return self.frente.dato if self.frente else None

    # Este metodo permite usar len(cola) para saber cuantos elementos hay
    def __len__(self):
        return self.tamanio
