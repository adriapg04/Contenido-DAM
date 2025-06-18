import unittest

# Importa las funciones a testear desde gestion_inventario.py
from gestion_inventario import calcular_valor_total, productos_agotados, actualizar_cantidad

class TestInventario(unittest.TestCase):

# Prueba que la funcion calcular_valor_total sume correctamente el valor total.
    def test_calcular_valor_total(self):
        # Se define un inventario de prueba:
        # Producto1: 100.0 * 2 = 200.0
        # Producto2: 50.0 * 3 = 150.0
        inventario = [
            ["Producto1", 100.0, 2],
            ["Producto2", 50.0, 3]
        ]
        # Se espera que el total sea 200.0 + 150.0 = 350.0
        self.assertEqual(calcular_valor_total(inventario), 350.0)

# Prueba que la funcion productos_agotados devuerlva los nombres de los productos con cantidad 0.
    def test_productos_agotados(self):
        # Se define un inventario de prueba con un producto agotado (cantidad 0)
        inventario = [
            ["Producto1", 100.0, 0],
            ["Producto2", 50.0, 3]
        ]
        # Se espera que devuelva una tupla con "Producto1"
        self.assertEqual(productos_agotados(inventario), ("Producto1",))

#  Prueba que actualizar_cantidad modifique la cantidad correctamente y devuelva true y que devuelva false al intentar actualizar un producto inexistente.
    def test_actualizar_cantidad(self):
        # Se define un inventario de prueba
        inventario = [
            ["Producto1", 100.0, 2],
            ["Producto2", 50.0, 3]
        ]
        # Actualiza la cantidad de "Producto1" a 5 y se espera true
        self.assertTrue(actualizar_cantidad(inventario, "Producto1", 5))
        # Verifica que la cantidad de "Producto1" se actualizo a 5
        self.assertEqual(inventario[0][2], 5)
        # Intenta actualizar un producto que no existe y se espera False
        self.assertFalse(actualizar_cantidad(inventario, "NoExiste", 10))

# Si se ejecuta este archivo directamente, se ejecutan las pruebas unitarias
if __name__ == "__main__":
    unittest.main()
