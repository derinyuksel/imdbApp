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
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(state = state, modifier = modifier)
}

@Composable
fun HomeScreenContent(state: HomeUiState, modifier: Modifier) {
    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error)
        }

        else -> {
            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn {

                    val sections = listOf(
                        "Trending" to state.trendingMovies,
                        "Popular" to state.popularMovies,
                        "Top Rated" to state.topRatedMovies,
                        "Upcoming" to state.upcomingMovies,
                        "Trending People" to state.trendingPeople
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

