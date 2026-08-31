package com.example.imdbapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class GenreResponse(
    val genres: List<Genre>
)