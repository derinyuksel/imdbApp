package com.example.imdbapp.home

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable

fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }
        state.error != null -> {
            Text (text = state.error!!)
        }
        else -> {
            Text (text = state.trendingMovies[1].title)
            Text (text = state.trendingMovies[1].overview)
        }
    }
}