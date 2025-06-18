package com.example.myapplication.huerto


// imports automaticos necesarios
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R

// definicion de una clase enumerada (enum) con las tres constantes (estados) del huerto
enum class EstadoHuerto {
    VACIO,
    PLANTAS,
    FRUTAS
}

// funcion que muestra la imagen segun el estado del huerto
@Composable // utiliza compose (interfaz) para dibujar la interfaz
fun MostrarImagen(
    estado: () -> EstadoHuerto,
    estadoEsperado: EstadoHuerto,
    imagenId: Int,
    descripcion: String
) {
    // if que hace que solo se muestre la imagen si el estado actual coincide con el estado esperado
    if (estado() == estadoEsperado) {
        Image(
            // carga de la imagen usando painterresource y el id del recurso (imagenId)
            painter = painterResource(id = imagenId),
            // contentDescription se usa para describir la imagen a lectores de pantalla
            contentDescription = descripcion,
            // modifier.size define el tamaño de la imagen (200 x 200 dp)
            modifier = Modifier.size(200.dp)
        )
    }
}

// funcion principal que dibuja el huerto y maneja el estado
@Composable
fun HuertoApp() {
    // uso de remember y mutablestateof que hacen que el compose recuerde el valor de las variables
    // estadoHuerto: define si el huerto esta vacio, con plantas o frutas
    var estadoHuerto by remember { mutableStateOf(EstadoHuerto.VACIO) }
    // valorEuros: numero que mostraremos cuando el huerto tiene frutas.
    var valorEuros by remember { mutableStateOf<Int?>(null) }
    // contadorRandom: contador para generar un valor entre 1 y 5
    var contadorRandom by remember { mutableStateOf(0) }

    // creacion de una columna que coloca sus elementos verticalmente, con fillmaxsize para que ocupe toda la pantalla, y alineaciones de los elementos
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // mostramos una imagen para cada estado posible
        MostrarImagen({ estadoHuerto }, EstadoHuerto.VACIO, R.drawable.huerto_vacio, "Huerto vacío")
        MostrarImagen({ estadoHuerto }, EstadoHuerto.PLANTAS, R.drawable.huerto_con_plantas, "Huerto con plantas")
        MostrarImagen({ estadoHuerto }, EstadoHuerto.FRUTAS, R.drawable.huerto_con_frutas, "Huerto con frutas")

        // if que muestra el valor si no es de 0 euros
        if (valorEuros != null) {
            Text(text = "Valor en euros: $valorEuros")
        }

        // espacio vertical para separar elementos
        Spacer(modifier = Modifier.height(20.dp))

        // boton que al pulsarlo cambia el estado del huerto
        Button(onClick = {
            // estructura when para decidir que hacer segun el estado actual del huerto
            when (estadoHuerto) {
                EstadoHuerto.VACIO -> {
                    // Si estaba vacio, pasa a tener plantas y borrar el valor en euros
                    estadoHuerto = EstadoHuerto.PLANTAS
                    valorEuros = null
                }
                EstadoHuerto.PLANTAS -> {
                    // Si tiene plantas, pasa a tener frutas
                    estadoHuerto = EstadoHuerto.FRUTAS
                }
                EstadoHuerto.FRUTAS -> {
                    // Si tiene frutas, calculamos un valor aleatorio entre 1 y 5
                    valorEuros = (contadorRandom % 5) + 1
                    contadorRandom++
                    // el huerti vuelve a estar vacio, reiniciando el ciclo.
                    estadoHuerto = EstadoHuerto.VACIO
                }
            }
        }) {
            // texto que aparece dentro del botón
            Text(text = "Cambiar Estado")
        }
    }
}


// preview para ver ejecucion de codigo en el prpio android studio
@Preview(showBackground = true)
@Composable
fun PreviewHuertoApp() {
    HuertoApp()
}

