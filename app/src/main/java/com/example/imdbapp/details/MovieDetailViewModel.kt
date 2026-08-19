package com.example.imdbapp.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.imdbapp.core.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.async
import com.example.imdbapp.core.NetworkResult


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repo: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: String = checkNotNull(savedStateHandle["movieId"])
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()


    private fun loadDetails() {

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val movieDeferred = async { repo.getMovieDetails(movieId.toInt()) }
            val castDeferred = async { repo.getMovieCredits(movieId.toInt()) }

            val movieResult = movieDeferred.await()
            val castResult = castDeferred.await()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,

                    movie = (movieResult as? NetworkResult.Success)?.data,
                    cast = (castResult as? NetworkResult.Success)?.data?.cast.orEmpty(),

                    error = (movieResult as? NetworkResult.Error)?.message
                        ?: (castResult as? NetworkResult.Error)?.message
                )
            }

        }

    }

    init {
        loadDetails()
    }

}
