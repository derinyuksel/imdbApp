package com.example.imdbapp.details
import com.example.imdbapp.model.Cast
import com.example.imdbapp.model.Result

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: Result? = null,
    val cast: List<Cast> = emptyList(),
    val error: String? = null,
)