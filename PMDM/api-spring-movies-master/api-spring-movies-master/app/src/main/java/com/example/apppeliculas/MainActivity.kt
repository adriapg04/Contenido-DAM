package com.example.apppeliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.apppeliculas.data.RetrofitClient
import com.example.apppeliculas.models.Movie
import com.example.apppeliculas.ui.MoviesScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado para almacenar la lista de películas
            val moviesState = remember { mutableStateOf<List<Movie>>(emptyList()) }

            // Llamar a la API solo una vez
            LaunchedEffect(Unit) {
                RetrofitClient.moviesApi.getMovies().enqueue(object : Callback<List<Movie>> {
                    override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                        if (response.isSuccessful) {
                            moviesState.value = response.body() ?: emptyList()
                        }
                    }
                    override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                        // Manejo simple de error (puedes mostrar un Toast o log)
                    }
                })
            }

            MoviesScreen(movies = moviesState.value)
        }
    }
}
