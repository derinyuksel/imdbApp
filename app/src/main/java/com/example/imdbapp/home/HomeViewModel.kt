package com.example.imdbapp.home

import android.net.Network
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbapp.core.NetworkResult
import com.example.imdbapp.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject
import kotlin.collections.orEmpty

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: Repository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadTrendingMovies()
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {

            supervisorScope {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                val trendingMovies = async { repo.getTrendingMovies() }
                val popularMovies = async { repo.getPopularMovies() }
                val topRatedMovies = async { repo.getTopRatedMovies() }
                val upcomingMovies = async { repo.getUpcomingMovies() }
                val trendingPeople = async { repo.getTrendingPeople() }


                val trending = trendingMovies.await()
                val popular = popularMovies.await()
                val topRated = topRatedMovies.await()
                val upcoming = upcomingMovies.await()
                val people = trendingPeople.await()


                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trendingMovies = (trending as? NetworkResult.Success)?.data.orEmpty(),
                        popularMovies = (popular as? NetworkResult.Success)?.data.orEmpty(),
                        topRatedMovies = (topRated as? NetworkResult.Success)?.data.orEmpty(),
                        upcomingMovies = (upcoming as? NetworkResult.Success)?.data.orEmpty(),
                        trendingPeople = (people as? NetworkResult.Success)?.data.orEmpty(),

                        //Error Catcher
                        error = (trending as? NetworkResult.Error)?.message
                            ?: (people as? NetworkResult.Error)?.message
                            ?: (popular as? NetworkResult.Error)?.message
                    )
                }


            }

        }
    }
}