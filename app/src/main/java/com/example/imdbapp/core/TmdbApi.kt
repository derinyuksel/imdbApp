package com.example.imdbapp.core

import retrofit2.http.GET


interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getTrendgingMovies() : MovieResponse

    }
}