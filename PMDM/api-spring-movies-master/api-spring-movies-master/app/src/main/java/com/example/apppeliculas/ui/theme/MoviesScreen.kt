package com.example.apppeliculas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apppeliculas.models.Movie

@Composable
fun MoviesScreen(movies: List<Movie>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    val titleColor = if (movie.title.equals("Avatar", ignoreCase = true)) {
        Color.Blue
    } else {
        MaterialTheme.colorScheme.onBackground
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = movie.title, color = titleColor, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Director: ${movie.director}")
        Text(text = "Año: ${movie.year}")
    }
}
