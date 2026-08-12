package com.example.imdbapp.home

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imdbapp.core.HomeViewModel

@Composable

fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        stateisLoading -> {
            CircularProgressIndicator()
        }
        state.error != null -> {
            Text (text = state.error!!)
        }
        else -> {
            Text (text = state.trendingMovies[0].title)
            Text (text = state.trendingMovies[0].overview)
        }
    }
}