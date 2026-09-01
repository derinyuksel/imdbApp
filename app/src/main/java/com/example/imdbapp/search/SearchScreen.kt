package com.example.imdbapp.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imdbapp.home.HomeViewModel
import com.example.imdbapp.home.MovieCard

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    var searchQuery by remember { mutableStateOf("") }


    val allMovies = (state.trendingMovies + state.popularMovies + state.topRatedMovies + state.upcomingMovies).distinctBy { it.id }


    val filteredMovies = if (searchQuery.isBlank()) {
        allMovies
    } else {
        allMovies.filter { movie ->
            movie.title?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Search Movies",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by title...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))


        if (filteredMovies.isEmpty()) {
            Text(text = "No movies found matching '$searchQuery'")
        } else {
            LazyColumn {
                items(filteredMovies) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClick = onMovieClick
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}