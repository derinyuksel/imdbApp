package com.example.imdbapp.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items

@Composable

fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error!!)
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn() {

                    val sections = listOf(
                        "Trending" to state.trendingMovies
                    )

                    items(sections) { (title, movies) ->
                        MoviesSection(
                            title = title,
                            movies = movies,
                            onMovieClick = {}
                            )
                    }

                }

            }
        }
    }
}

@Composable
fun HomeScreenContent() {

}

