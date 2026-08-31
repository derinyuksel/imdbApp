package com.example.imdbapp.details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.imdbapp.home.MovieCard


@Composable
fun PersonDetailScreen(
    onBackClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {},
    viewModel: PersonDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    PersonDetailContent(
        state = state,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick)
}


@Composable
fun PersonDetailContent(
    state: PersonDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {
    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error)
        }

        state.person != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Go back button
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                // Profile Photo
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500" + state.person.profilePath,
                    contentDescription = state.person.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentScale = ContentScale.Crop
                )

                // Person Information
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = state.person.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!state.person.birthday.isNullOrEmpty()) {
                        Text(
                            text = "Born: ${state.person.birthday}" +
                                    if (!state.person.placeOfBirth.isNullOrEmpty()) " in ${state.person.placeOfBirth}" else "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    //Biography
                    Text(
                        text = state.person.biography ?: "No biography available.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))


                    // Cinematography
                    if (state.movies.isNotEmpty()) {
                        Text(
                            text = "Known For",
                            style = MaterialTheme.typography.titleLarge
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(top = 8.dp)
                        ) {
                            items(state.movies) { movie ->
                                MovieCard(
                                    movie = movie,
                                    onMovieClick = onMovieClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
