package com.example.apppeliculas.data

import com.example.apppeliculas.models.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MoviesApi {
    @GET("movies")
    fun getMovies(): Call<List<Movie>>
}
