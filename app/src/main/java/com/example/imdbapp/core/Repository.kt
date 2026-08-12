package com.example.imdbapp.core

import com.example.imdbapp.model.Result
import javax.inject.Inject

class Repository @Inject constructor(val apiService: TmdbApi) {

    suspend fun getTrendingMovies(): NetworkResult<List<Result>> {
        return try {
            val response = apiService.getTrendingMovies()
            NetworkResult.Success(response.results)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Something went wrong")
        }
    }
}