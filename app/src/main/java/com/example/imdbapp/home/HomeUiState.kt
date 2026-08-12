package com.example.imdbapp.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovies: List<MoveResponse.Result> = emptyList(),
    val error: String? = null
)
