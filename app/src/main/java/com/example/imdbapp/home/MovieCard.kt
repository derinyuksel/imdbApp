package com.example.imdbapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imdbapp.model.Result
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

@Composable
fun MovieCard(
    movie: Result,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .clickable { onMovieClick(movie.id) },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        val imagePath = movie.posterPath ?: movie.profilePath
        val displayText = movie.title ?: movie.name

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + imagePath,
            contentDescription = displayText,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .aspectRatio(2f / 3f),
            contentScale = ContentScale.Crop
        )


    }
}