package com.example.imdbapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast( //Blueprint for actors
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String? = null,
    val character: String
)

@Serializable
data class MovieCreditsResponse(
    val cast: List<Cast>
)
@Serializable
data class PersonMovieCreditsResponse(
    val cast: List<Result>
)
@Serializable
data class PersonDetailResponse(
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String? = null,
    val biography: String? = null,
    val birthday: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null
)