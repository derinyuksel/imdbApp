package com.example.imdbapp.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imdbapp.model.Genre

@Composable
fun FilterSection(
    selectedType: String,
    selectedGenreId: Int?,
    selectedYear: String = "All",
    selectedMinRating: Double? = null,
    genres: List<Genre>,
    onTypeSelected: (String) -> Unit,
    onGenreSelected: (Int?) -> Unit,
    onYearSelected: (String) -> Unit = {},
    onRatingSelected: (Double?) -> Unit = {},
            modifier: Modifier = Modifier
) {
    // 1. Filter lists
    val types = listOf("All", "Movies", "TV Series", "Actors")
    val years = listOf("All", "2020s", "2010s", "Classics")
    val ratings = listOf(
        "All Ratings" to null,
        "8+ ⭐" to 8.0,
        "7+ ⭐" to 7.0,
        "6+ ⭐" to 6.0
    )

    Column(modifier = modifier.padding(vertical = 8.dp)) {

        // ROW 1: Content Types
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(types) { type ->
                FilterChip(
                    selected = (selectedType == type),
                    onClick = { onTypeSelected(type) },
                    label = { Text(text = type) }
                )
            }
        }

        // ROW 2: Genres
        if (selectedType != "Actors" && genres.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                item {
                    FilterChip(
                        selected = (selectedGenreId == null),
                        onClick = { onGenreSelected(null) },
                        label = { Text(text = "All Genres") }
                    )
                }

                items(genres) { genre ->
                    FilterChip(
                        selected = (selectedGenreId == genre.id),
                        onClick = { onGenreSelected(genre.id) },
                        label = { Text(text = genre.name) }
                    )
                }
            }
        }

        // ROW 3: Years
        if (selectedType != "Actors") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                items(years) { year ->
                    FilterChip(
                        selected = (selectedYear == year),
                        onClick = { onYearSelected(year) },
                        label = { Text(text = if (year == "All") "All Years" else year) }
                    )
                }
            }
        }

        // ROW 4: IMDb Ratings
        if (selectedType != "Actors") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                items(ratings) { (label, minRating) ->
                    FilterChip(
                        selected = (selectedMinRating == minRating),
                        onClick = { onRatingSelected(minRating) },
                        label = { Text(text = label) }
                    )
                }
            }
        }
    }
}