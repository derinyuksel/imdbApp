package com.example.imdbapp.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbapp.core.Repository
import com.example.imdbapp.model.Result
import com.example.imdbapp.model.WatchlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {


    val watchlistItems: StateFlow<List<Result>> = repo.getWatchlist()
        .map { movies ->
            movies.map { it.toResult() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeFromWatchlist(movie: Result) {
        viewModelScope.launch {
            repo.removeFromWatchlist(movie.toWatchlistMovie())
        }
    }
}

fun WatchlistMovie.toResult() = Result(
    id = id,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage
)

fun Result.toWatchlistMovie() = WatchlistMovie(
    id = id,
    title = title ?: "",
    posterPath = posterPath,
    voteAverage = voteAverage
)