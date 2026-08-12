package com.example.imdbapp.home

import com.example.imdbapp.model.Result

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovies: List<Result> = emptyList(),
    val error: String? = null
)
