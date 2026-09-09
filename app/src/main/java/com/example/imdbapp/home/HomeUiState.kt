package com.example.imdbapp.home

import com.example.imdbapp.model.Genre
import com.example.imdbapp.model.Result

data class HomeUiState(
    val isLoading: Boolean = false,
    val featuredItems: List<Result> = emptyList(),
    val selectedType: ContentType = ContentType.ALL, //enum clas olsun. String yerine ContentType yaptım HomeFilters icinde
    val selectedGenreId: Int? = null,
    val selectedYear: YearFilter = YearFilter.ALL, //String yerine YearFilter oldu HomeFilters icinde
    val selectedMinRating: RatingFilter = RatingFilter.ALL, //Double yerine RatingFilter oldu, HomeFilters icinde
    val genres: List<Genre> = emptyList(),
    val trendingMovies: List<Result> = emptyList(),
    val popularMovies: List<Result> = emptyList(),
    val upcomingMovies: List<Result> = emptyList(),
    val topRatedMovies: List<Result> = emptyList(),
    val tvShows: List<Result> =emptyList(),
    val trendingPeople: List<Result> = emptyList(),
    val error: String? = null,
    val watchlistIds: Set<Int> = emptySet()
)
