# definicion de las asignaturas
asignaturas = ("Matemáticas", "Historia", "Lengua")

# funcion para ingresar un estudiante
def ingresar_estudiante():
    nombre = input("Nombre del estudiante: ")
    edad = int(input("Edad del estudiante: "))
    calificaciones = {}
    for asignatura in asignaturas:
        calificaciones[asignatura] = float(input(f"Nota en {asignatura}: "))
    return {"nombre": nombre, "edad": edad, "calificaciones": calificaciones}

# funcion para mostrar la información de un estudiante
def mostrar_estudiante(estudiante):
    print(f"Nombre: {estudiante['nombre']}")
    print(f"Edad: {estudiante['edad']}")
    for asignatura, nota in estudiante["calificaciones"].items():
        print(f"{asignatura}: {nota}")
    promedio = sum(estudiante["calificaciones"].values()) / 3
    print(f"Promedio: {promedio:.2f}")


# programa principal
estudiante1 = ingresar_estudiante()
estudiante2 = ingresar_estudiante()
estudiante3 = ingresar_estudiante()

mostrar_estudiante(estudiante1)
mostrar_estudiante(estudiante2)
mostrar_estudiante(estudiante3)