def limpiar_terminal():
    # impresion de lineas que hacen de separador entre lo que elije un jugador y otro
    print("\n" * 50)
    print("=" * 80)
    print("NUEVO TURNO")
    print("=" * 80)
    print("\n" * 5)
# funcion que valida que la celda tenga formato letra+numero
def validar_celda(celda, max_col, max_row) -> bool:
    # devuelve True si la parte alfabetica y numerica son correctas
    return celda.isalpha() and celda[1:].isdigit()

# funcion que verifica que la celda no este ocupada en el equipo dado
def comprobar_celda_disponible(celda, equipo) -> bool:
    # devuelve True si la celda no coincide con la posicion de ningun personaje
    return celda not in [p.posicion for p in equipo]