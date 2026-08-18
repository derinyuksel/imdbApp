package com.example.imdbapp.core

import com.example.imdbapp.model.MovieResponse
import retrofit2.http.GET


interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse


    @GET("trending/people/week")
    suspend fun getTrendingPeople(): MovieResponse
}

