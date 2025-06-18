import os

# Carga el inventario desde el archivo 'inventario.txt'. Si el archivo no existe, lo crea
def cargar_inventario():
    # Lista para almacenar los productos
    inventario = []
    # Verifica si el archivo 'inventario.txt' existe en el directorio actual
    if not os.path.exists("inventario.txt"):
        # Si no existe, lo crea y escribe el inventario inicial
        with open("inventario.txt", "w", encoding="utf-8") as f:
            f.write("Portatil,799.99,10\n")
            f.write("Telefono,299.99,5\n")
            f.write("Tablet,499.99,10\n")
            f.write("Auriculares,59.99,15\n")
            f.write("Monitor,199.99,23\n")
    # Abre el archivo para leer el contenido
    with open("inventario.txt", "r", encoding="utf-8") as f:
        # Recorre cada linea del archivo
        for linea in f:
            # Elimina espacios en blanco y separa la linea por comas
            datos = linea.strip().split(",")
            # Solo procesa lineas con exactamente 3 datos: nombre, precio y cantidad
            if len(datos) == 3:
                # Primer dato: nombre del producto
                nombre = datos[0]
                # Segundo dato: precio convertido a float
                precio = float(datos[1])
                # Tercer dato: cantidad convertido a entero
                cantidad = int(datos[2])
                # Agrega el producto a la lista de inventario
                inventario.append([nombre, precio, cantidad])
    # Devuelve la lista completa de productos
    return inventario

# Metodo que muestra el inventario
def mostrar_inventario(inventario):
    # Imprime la cabecera con los nombres de las columnas
    print("Producto\tPrecio\tCantidad")
    # Recorre cada producto de la lista
    for prod in inventario:
        # Imprime el nombre, el precio con dos decimales y la cantidad
        print(f"{prod[0]}\t{prod[1]:.2f}\t{prod[2]}")

# Metodo que calcula el valor total del inventario
def calcular_valor_total(inventario):
    # Inicializa el total en 0
    total = 0
    # Recorre cada producto en el inventario
    for prod in inventario:
        # Acumula el producto del precio por la cantidad
        total += prod[1] * prod[2]
    # Devuelve el valor total calculado
    return total

# Metodo que devuelve los productos agotados
def productos_agotados(inventario):
    # Devuelve una tupla con el nombre de cada producto que tiene cantidad 0
    return tuple(prod[0] for prod in inventario if prod[2] == 0)

# Metodo que actualiza la cantidad de los productos
def actualizar_cantidad(inventario, nombre, nueva_cantidad):
    # Recorre cada producto del inventario
    for prod in inventario:
        # Compara el nombre del producto ignorando mayusculas y minusculas
        if prod[0].lower() == nombre.lower():
            # Actualiza la cantidad con la nueva cantidad
            prod[2] = nueva_cantidad
            # Devuelve true indicando que se encontro y actualizo el producto
            return True
    # Si el producto no se encontro, develve false
    return False

# Metodo que guarda el inventario
def guardar_inventario(inventario):
    # Abre el archivo en modo escritura para sobrescribir el contenido anterior
    with open("inventario.txt", "w", encoding="utf-8") as f:
        # Itera sobre cada producto en el inventario
        for prod in inventario:
            # Escribe cada producto en formato "nombre,precio,cantidad"
            f.write(f"{prod[0]},{prod[1]},{prod[2]}\n")

# Funcion principal que muestra un  menu para gestionar el inventario
def main():
    # Carga o crea el inventario desde/para el archivo
    inventario = cargar_inventario()
    # Bucle infinito hasta que el usuario decida salir
    while True:
        # Muestra el menu de opciones
        print("\nMenu:")
        print("1. Mostrar inventario")
        print("2. Calcular valor total del inventario")
        print("3. Mostrar productos agotados")
        print("4. Actualizar cantidad de un producto")
        print("5. Salir")
        # Lee la opcion ingresada por el usuario
        opcion = input("Elige una opcion: ")
        if opcion == "1":
            # Opcion para mostrar el inventario
            mostrar_inventario(inventario)
        elif opcion == "2":
            # Calcula el valor total del inventario
            total = calcular_valor_total(inventario)
            print("Valor total del inventario: $", total)
        elif opcion == "3":
            # Obtiene los productos agotados (cantidad 0)
            agots = productos_agotados(inventario)
            print("Productos agotados:", agots)
        elif opcion == "4":
            # Pide al usuario el nombre del producto a actualizar
            nombre = input("Nombre del producto: ")
            # Intenta convertir la nueva cantidad a entero
            try:
                nueva_cantidad = int(input("Nueva cantidad: "))
            except ValueError:
                print("Cantidad invalida.")
                continue
            # Actualiza la cantidad; si el producto se encuentra, guarda los cambios
            if actualizar_cantidad(inventario, nombre, nueva_cantidad):
                # Guarda el inventario actualizado en el archivo
                guardar_inventario(inventario)
                print("Cantidad actualizada.")
            else:
                print("Producto no encontrado.")
        elif opcion == "5":
            # Opcion para salir del programa; rompe el bucle infinito
            break
        else:
            # Mensaje si la opcion ingresada no es correcta
            print("Opcion no valida.")

# Si este archivo se ejecuta directamente, llama a la funcion principal
if __name__ == "__main__":
    main()
