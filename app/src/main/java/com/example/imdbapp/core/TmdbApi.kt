package com.example.imdbapp.core

import com.example.imdbapp.model.MovieCreditsResponse
import com.example.imdbapp.model.MovieResponse
import com.example.imdbapp.model.PersonMovieCreditsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.imdbapp.model.Result


interface TmdbApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): MovieResponse

    @GET("person/popular")
    suspend fun getTrendingPeople(): MovieResponse

    @GET ("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Result

    @GET ("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") id: Int): MovieCreditsResponse

    @GET ("person/{person_id}/movie_credits")
    suspend fun getPersonMovies(@Path("person_id") id: Int): PersonMovieCreditsResponse





}

