package com.example.imdbapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imdbapp.model.Result
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.FavoriteBorder
import com.example.imdbapp.ui.theme.LilacPrimary
import com.example.imdbapp.ui.theme.Purple40

@Composable
fun MovieCard(
    movie: Result,
    isFavorite: Boolean = false,
    onToggleFavorite: () -> Unit = {},
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
        Box {
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
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite)  MaterialTheme.colorScheme.tertiary else Color.White
                )
            }
        }
    }
}
