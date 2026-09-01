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
import com.example.imdbapp.model.Result

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
        onGenreSelected = { genreId -> viewModel.selectGenreFilter(genreId) }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {},
    onPersonClick: (Int) -> Unit = {},
    onTypeSelected: (String) -> Unit = {},
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
            //Filter movies & TV series lists by selected genre ID
            val filteredTrending = filterByGenre(state.trendingMovies, state.selectedGenreId)
            val filteredPopular = filterByGenre(state.popularMovies, state.selectedGenreId)
            val filteredTopRated = filterByGenre(state.topRatedMovies, state.selectedGenreId)
            val filteredUpcoming = filterByGenre(state.upcomingMovies, state.selectedGenreId)
            val filteredTv = filterByGenre(state.tvShows, state.selectedGenreId)

            val sections = mutableListOf<Pair<String, List<Result>>>()

            if (state.selectedType == "All" || state.selectedType == "Movies") {
                sections.add("Trending" to filteredTrending)
                sections.add("Popular" to filteredPopular)
                sections.add("Top Rated" to filteredTopRated)
                sections.add("Upcoming" to filteredUpcoming)
            }

            if (state.selectedType == "All" || state.selectedType == "TV Series") {
                sections.add("Popular TV Series" to filteredTv)
            }

            if (state.selectedType == "All" || state.selectedType == "Actors") {
                sections.add("Trending People" to state.trendingPeople)
            }

            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn {
                    // Filter buttons on the top
                    item {
                        FilterSection(
                            selectedType = state.selectedType,
                            selectedGenreId = state.selectedGenreId,
                            genres = state.genres,
                            onTypeSelected = onTypeSelected,
                            onGenreSelected = onGenreSelected
                        )
                    }

                    // Movies, series, people section
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
    if (genreId == null) return items
    return items.filter { item -> item.genreIds?.contains(genreId) == true }
}



