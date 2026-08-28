package com.example.imdbapp.details
import com.example.imdbapp.model.Result
import com.example.imdbapp.model.PersonDetailResponse

data class PersonDetailUiState(
    val isLoading: Boolean = false,
    val person: PersonDetailResponse? = null,
    val movies: List<Result> = emptyList(),
    val error: String? = null
)