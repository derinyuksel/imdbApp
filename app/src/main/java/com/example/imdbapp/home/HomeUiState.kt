package com.example.imdbapp.home

import com.example.imdbapp.model.Result

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovies: List<Result> = emptyList(),
    val popularMovies: List<Result> = emptyList(),
    val upcomingMovies: List<Result> = emptyList(),
    val topRatedMovies: List<Result> = emptyList(),
    val trendingPeople: List<Result> = emptyList(),
    val error: String? = null
)
