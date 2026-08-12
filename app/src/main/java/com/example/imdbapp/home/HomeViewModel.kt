package com.example.imdbapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbapp.core.NetworkResult
import com.example.imdbapp.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: Repository
) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    init {
        loadTrendingMovies()
    }

    fun loadTrendingMovies(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = repo.getTrendingMovies()

            _uiState.update { state ->
                when (result){
                    is NetworkResult.Success -> state.copy(trendingMovies = result.data, isLoading = false)
                    is NetworkResult.Error -> state.copy(error = result.message, isLoading = false)
                }
            }

        }
    }
}