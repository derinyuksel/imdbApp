package com.example.imdbapp.core

import com.example.imdbapp.model.MovieResponse
import javax.inject.Inject

class Repository @Inject constructor(val apiService: TmdbApi) {

    suspend fun getTrendingMovies(): NetworkResult<List<MovieResponse.Result>> {
        return try {
            val response = apiService.getTrendingMovies()
            NetworkResult.Success(response.results)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Something went wrong")
        }
    }
}