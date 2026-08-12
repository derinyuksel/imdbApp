package com.example.imdbapp.core

import com.example.imdbapp.model.MovieResponse
import retrofit2.http.GET


interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies() : MovieResponse

    }
}