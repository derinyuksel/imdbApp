package com.example.imdbapp.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import kotlin.collections.filter
import com.example.imdbapp.model.Result
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        modifier = modifier,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick,
        onTypeSelected = { type -> viewModel.selectTypeFilter(type) },
        onGenreSelected = { genreId -> viewModel.selectGenreFilter(genreId) }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {},
    onPersonClick: (Int) -> Unit = {},
    onTypeSelected: (String) -> Unit = {},
    onGenreSelected: (Int?) -> Unit = {}
) {
    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.error != null -> {
            Text(text = state.error)
        }

        else -> {
            //Filter movies & TV series lists by selected genre ID
            val filteredTrending = filterByGenre(state.trendingMovies, state.selectedGenreId)
            val filteredPopular = filterByGenre(state.popularMovies, state.selectedGenreId)
            val filteredTopRated = filterByGenre(state.topRatedMovies, state.selectedGenreId)
            val filteredUpcoming = filterByGenre(state.upcomingMovies, state.selectedGenreId)
            val filteredTv = filterByGenre(state.tvShows, state.selectedGenreId)

            val sections = mutableListOf<Pair<String, List<Result>>>()

            if (state.selectedType == "All" || state.selectedType == "Movies") {
                sections.add("Trending" to filteredTrending)
                sections.add("Popular" to filteredPopular)
                sections.add("Top Rated" to filteredTopRated)
                sections.add("Upcoming" to filteredUpcoming)
            }

            if (state.selectedType == "All" || state.selectedType == "TV Series") {
                sections.add("Popular TV Series" to filteredTv)
            }

            if (state.selectedType == "All" || state.selectedType == "Actors") {
                sections.add("Trending People" to state.trendingPeople)
            }

            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn {
                    if (state.trendingMovies.isNotEmpty()) {
                        item {
                            FeaturedBanner(
                                movie = state.trendingMovies.first(),
                                onMovieClick = onMovieClick,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Filter buttons on the top
                    item {
                        FilterSection(
                            selectedType = state.selectedType,
                            selectedGenreId = state.selectedGenreId,
                            genres = state.genres,
                            onTypeSelected = onTypeSelected,
                            onGenreSelected = onGenreSelected
                        )
                    }

                    // Movies, series, people section
                    items(sections) { (title, movies) ->
                        MoviesSection(
                            title = title,
                            movies = movies,
                            onMovieClick = { id ->
                                if (title == "Trending People") onPersonClick(id)
                                else onMovieClick(id)
                            }
                        )
                    }
                }
            }
        }


    }


}

fun filterByGenre(items: List<Result>, genreId: Int?): List<Result> {
    if (genreId == null) return items
    return items.filter { item -> item.genreIds?.contains(genreId) == true }
}


@Composable
fun FeaturedBanner(
    movie: Result,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    //Opens Youtube URLs
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { onMovieClick(movie.id) },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Featured image
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500" + (movie.backdropPath ?: movie.posterPath),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
            )

            // Movie Title & Watch Trailer Button
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = movie.title ?: movie.name ?: "Featured Movie",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Watch trailer button
                Button(
                    onClick = {
                        // Opens YouTube trailer placeholder URL in device browser or YouTube app!
                        uriHandler.openUri("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Trailer",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Watch Trailer", color = Color.White)
                }
            }
        }
    }
}
