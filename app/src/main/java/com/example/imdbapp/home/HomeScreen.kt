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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.imdbapp.util.shimmer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    scrollToTopTrigger: Int = 0,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(scrollToTopTrigger) {
        if (scrollToTopTrigger > 0) {
            listState.animateScrollToItem(0)
        }
    }

    HomeScreenContent(
        state = state,
        listState = listState,
        modifier = modifier,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick,
        onTypeSelected = { type -> viewModel.selectTypeFilter(type) },
        onGenreSelected = { genreId -> viewModel.selectGenreFilter(genreId) },
        onYearSelected = { year -> viewModel.selectYearFilter(year) },
        onRatingSelected = { rating -> viewModel.selectRatingFilter(rating) },
        onToggleWatchlist = { movie -> viewModel.toggleWatchlist(movie) }
    )
}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {},
    onPersonClick: (Int) -> Unit = {},
    onTypeSelected: (ContentType) -> Unit = {},
    onGenreSelected: (Int?) -> Unit = {},
    onYearSelected: (YearFilter) -> Unit = {},
    onRatingSelected: (RatingFilter) -> Unit = {},
    onToggleWatchlist: (Result) -> Unit = {}
) {
    when {state.isLoading -> {
        Column(modifier = Modifier.padding(16.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .shimmer()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmer()
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .shimmer()
                    )
                }
            }
        }
    }




        state.error != null -> {
            Text(text = state.error)
        }

        else -> {
            val filteredTrending = filterByRating(filterByYear(filterByGenre(state.trendingMovies, state.selectedGenreId), state.selectedYear), state.selectedMinRating)
            val filteredPopular = filterByRating(filterByYear(filterByGenre(state.popularMovies, state.selectedGenreId), state.selectedYear), state.selectedMinRating)
            val filteredTopRated = filterByRating(filterByYear(filterByGenre(state.topRatedMovies, state.selectedGenreId), state.selectedYear), state.selectedMinRating)
            val filteredUpcoming = filterByRating(filterByYear(filterByGenre(state.upcomingMovies, state.selectedGenreId), state.selectedYear), state.selectedMinRating)
            val filteredTv = filterByRating(filterByYear(filterByGenre(state.tvShows, state.selectedGenreId), state.selectedYear), state.selectedMinRating)
            val filteredClassics = filterByRating(filterByYear(filterByGenre(state.classicMovies, state.selectedGenreId), state.selectedYear), state.selectedMinRating)

            val sections = mutableListOf<Pair<String, List<Result>>>()

            if (state.selectedType == ContentType.ALL || state.selectedType == ContentType.MOVIES) {
                sections.add("Trending" to filteredTrending)
                sections.add("Popular" to filteredPopular)
                sections.add("Top Rated" to filteredTopRated)
                sections.add("Upcoming" to filteredUpcoming)
                sections.add("All-Time Hits" to filteredClassics)
            }


            if (state.selectedType == ContentType.ALL || state.selectedType == ContentType.TV_SERIES) {
                sections.add("Popular TV Series" to filteredTv)
            }

            if (state.selectedType == ContentType.ALL || state.selectedType == ContentType.ACTORS) {
                sections.add("Trending People" to state.trendingPeople)
            }

            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn {
                    if (state.trendingMovies.isNotEmpty()) {
                        item {
                            FeaturedPager(
                                items = state.featuredItems,
                                onItemClick = onMovieClick,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                    }

                   item{ Spacer(modifier = Modifier.height(12.dp))}

                    // Filter buttons on top
                    item {
                        FilterSection(
                            selectedType = state.selectedType,
                            selectedGenreId = state.selectedGenreId,
                            selectedYear = state.selectedYear,
                            selectedMinRating = state.selectedMinRating,
                            genres = state.genres,
                            onTypeSelected = onTypeSelected,
                            onGenreSelected = onGenreSelected,
                            onYearSelected = onYearSelected,
                            onRatingSelected = onRatingSelected
                        )
                    }


                  item{  Spacer(modifier = Modifier.height(28.dp))}

                    items(sections) { (title, movies) ->
                        MoviesSection(
                            title = title,
                            movies = movies,
                            watchlistIds = state.watchlistIds,
                            onToggleWatchlist = onToggleWatchlist,
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

fun filterByYear(items: List<Result>, selectedYear: YearFilter): List<Result> {
    if (selectedYear == YearFilter.ALL) return items

    return items.filter { movie ->
        val year = movie.releaseDate?.take(4)?.toIntOrNull()
        when (selectedYear) {
            YearFilter.Y2020S -> year != null && year >= 2020
            YearFilter.Y2010S -> year != null && year in 2010..2019
            YearFilter.CLASSICS -> year != null && year < 2010
            else -> true
        }
    }
}


fun filterByRating(items: List<Result>, ratingFilter: RatingFilter): List<Result> {
    if (ratingFilter == RatingFilter.ALL) return items

    return items.filter { movie ->
        movie.voteAverage != null && movie.voteAverage >= (ratingFilter.minRating ?: 0.0)
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

    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable { onMovieClick(movie.id) },
        shape = RoundedCornerShape(28.dp)
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
                        //placeholder for every movie
                        uriHandler.openUri("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
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
