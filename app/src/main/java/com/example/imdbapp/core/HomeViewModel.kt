package com.example.imdbapp.core

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo : Repository
) : ViewModel() {
}

private val _uiState = MutableStateFlow(HomeUiState())