package com.example.imdbapp.home

import com.example.imdbapp.model.Genre
import com.example.imdbapp.model.Result

data class HomeUiState(
    val isLoading: Boolean = false,
    val selectedType: String = "All", //enum clas olsun
    val selectedGenreId: Int? = null,
    val selectedYear: String = "All",
    val selectedMinRating: Double? = null,
    val genres: List<Genre> = emptyList(),
    val trendingMovies: List<Result> = emptyList(),
    val popularMovies: List<Result> = emptyList(),
    val upcomingMovies: List<Result> = emptyList(),
    val topRatedMovies: List<Result> = emptyList(),
    val tvShows: List<Result> =emptyList(),
    val trendingPeople: List<Result> = emptyList(),
    val error: String? = null,
    val watchlistIds: Set<Int> = emptySet()
)
