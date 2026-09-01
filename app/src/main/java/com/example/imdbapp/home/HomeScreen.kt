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
import kotlin.collections.filter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        modifier = modifier,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick,
        onTypeSelected = { type -> viewModel.selectTypeFilter(type) },
        onGenreSelcted = { genreId -> viewModel.selectGenreFilter(genreId) }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {},
    onPersonClick: (Int) -> Unit = {},
    onTypeSelected: (String) -> Unit ={},
    onGenreSelected: (Int?) -> Unit = {}
) {
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
                            onMovieClick = { id ->
                                if (title == "Trending People") onPersonClick(id)
                                else onMovieClick(id)
                            }
                        )
                    }

                }

            }
        }
    }
}


fun filterByGenre(items: List<Result>, genreId: Int?): List<Result> {
    if (genreId == null) return items //If no genre is selected, keeps everything
    return items.filter { item -> item.genreIds?.contains(genreId) == true}
}

