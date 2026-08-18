package com.example.imdbapp.core

import com.example.imdbapp.model.MovieResponse
import com.example.imdbapp.model.Result
import javax.inject.Inject

class Repository @Inject constructor(val apiService: TmdbApi) {

    suspend fun getTrendingMovies()= getHomeData { apiService.getTrendingMovies() }
    suspend fun getPopularMovies()= getHomeData { apiService.getPopularMovies() }
    suspend fun getTopRatedMovies()= getHomeData { apiService.getTopRatedMovies() }
    suspend fun getUpcomingMovies()= getHomeData { apiService.getUpcomingMovies() }
    suspend fun getTrendingPeople()= getHomeData { apiService.getTrendingPeople() }







    suspend fun getHomeData(
        apiCall: suspend ()-> MovieResponse
    ) : NetworkResult<List<Result>>{
        return try {
            val response = apiCall()
            NetworkResult.Success(response.results)
        } catch (e: Exception){
            NetworkResult.Error(e.message ?: "Something went wrong")
        }
    }









}