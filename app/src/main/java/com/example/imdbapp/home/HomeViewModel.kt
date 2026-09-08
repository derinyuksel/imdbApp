package com.example.imdbapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbapp.core.NetworkResult
import com.example.imdbapp.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import kotlin.collections.orEmpty
import com.example.imdbapp.model.Result

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: Repository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadTrendingMovies()
        observeWatchlist()
    }

    private fun observeWatchlist() {
        viewModelScope.launch {
            repo.getWatchlist().collect { watchlist ->
                _uiState.update { it.copy(watchlistIds = watchlist.map { it.id }.toSet()) }
            }
        }
    }

    fun loadTrendingMovies() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repo.getTrendingMovies()
            _uiState.update {
                it.copy(
                    trendingMovies = (result as? NetworkResult.Success)?.data.orEmpty(),
                    // Hide main shimmer once the first big piece of data arrives
                    isLoading = false,
                    error = if (result is NetworkResult.Error) result.message else it.error
                )
            }
        }

        viewModelScope.launch {
            val result = repo.getPopularMovies()
            _uiState.update { it.copy(popularMovies = (result as? NetworkResult.Success)?.data.orEmpty()) }
        }

        viewModelScope.launch {
            val result = repo.getTopRatedMovies()
            _uiState.update { it.copy(topRatedMovies = (result as? NetworkResult.Success)?.data.orEmpty()) }
        }

        viewModelScope.launch {
            val result = repo.getUpcomingMovies()
            _uiState.update { it.copy(upcomingMovies = (result as? NetworkResult.Success)?.data.orEmpty()) }
        }

        viewModelScope.launch {
            val result = repo.getTrendingPeople()
            _uiState.update { it.copy(trendingPeople = (result as? NetworkResult.Success)?.data.orEmpty()) }
        }

        viewModelScope.launch {
            val result = repo.getPopularTvShows()
            _uiState.update { it.copy(tvShows = (result as? NetworkResult.Success)?.data.orEmpty()) }
        }


        viewModelScope.launch {
            val result = repo.getMovieGenres()
            _uiState.update { it.copy(genres = (result as? NetworkResult.Success)?.data?.genres.orEmpty()) }
        }
    }

    fun selectTypeFilter(type: String) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun selectGenreFilter(genreId: Int?) {
        _uiState.update { state ->
            val newGenreId = if (state.selectedGenreId == genreId) null else genreId
            state.copy(selectedGenreId = newGenreId)
        }
    }

    fun selectYearFilter(year: String) {
        _uiState.update { it.copy(selectedYear = year) }
    }

    fun selectRatingFilter(minRating: Double?) {
        _uiState.update { state ->
            // Toggle off if tapping the same rating again
            val newRating = if (state.selectedMinRating == minRating) null else minRating
            state.copy(selectedMinRating = newRating)
        }
    }

    fun toggleWatchlist(movie: Result) {
        viewModelScope.launch {
            val isFavorite = _uiState.value.watchlistIds.contains(movie.id)
            if (isFavorite) {
                repo.removeFromWatchlist(
                    com.example.imdbapp.model.WatchlistMovie(
                        movie.id,
                        movie.title ?: "",
                        movie.posterPath,
                        movie.voteAverage
                    )
                )
            } else {
                repo.addToWatchlist(
                    com.example.imdbapp.model.WatchlistMovie(
                        movie.id,
                        movie.title ?: "",
                        movie.posterPath,
                        movie.voteAverage
                    )
                )
            }
        }
    }
}