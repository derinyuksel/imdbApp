package com.example.imdbapp.details

import androidx.lifecycle.SavedStateHandle
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
import javax.inject.Inject


@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val repo: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //Get personId from navigation
    private val personId: String = checkNotNull(savedStateHandle["personId"])

    private val _uiState = MutableStateFlow(PersonDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPersonDetails()
    }

    private fun loadPersonDetails() {
        viewModelScope.launch {
            //Show the loading spinner
            _uiState.update { it.copy(isLoading = true) }

            val personDeferred = async { repo.getPersonDetails(personId.toInt()) }
            val moviesDeferred = async { repo.getPersonMovies(personId.toInt()) }

            val personResult = personDeferred.await()
            val moviesResult = moviesDeferred.await()

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    person = (personResult as? NetworkResult.Success)?.data,
                    movies = (moviesResult as? NetworkResult.Success)?.data?.cast.orEmpty(),
                    error = (personResult as? NetworkResult.Error)?.message
                        ?: (moviesResult as? NetworkResult.Error)?.message
                )
            }
        }
    }


}