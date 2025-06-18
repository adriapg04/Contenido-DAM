import os  # Importamos una libreria para ejecutar comandos del sistema

def limpiar_terminal():
    """
    Limpia la pantalla de la consola para que no se vea lo anterior.
    Usa 'cls' si el sistema es Windows, y 'clear' si es Linux o Mac.
    """
    comando = 'cls' if os.name == 'nt' else 'clear'  # Detectamos el sistema operativo
    os.system(comando)  # Ejecutamos el comando en la terminal

def validar_celda(celda, max_col=4, max_row=4) -> bool:
    """
    Revisa que la casilla escrita por el jugador tenga el formato correcto.
    El formato correcto es una letra entre A y D, seguida de un numero entre 1 y 4.
    Ejemplo valido: B3
    """
    if len(celda) < 2:  # Si la celda es demasiado corta, no es valida
        return False
    letra = celda[0].upper()     # Tomamos la letra y la pasamos a mayuscula
    numero = celda[1:]           # Tomamos el resto, que deberia ser un numero
    return letra in 'ABCD' and numero.isdigit() and 1 <= int(numero) <= 4
    # Devuelve True solo si cumple todos los requisitos

def comprobar_celda_disponible(celda, equipo) -> bool:
    """
    Comprueba si una casilla esta libre en el tablero, es decir, que ningun personaje la este usando.
    """
    return celda not in [p.posicion for p in equipo]
    # Devuelve True si ningun personaje del equipo esta en esa celda
