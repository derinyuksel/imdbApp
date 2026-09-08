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
    selectedType: ContentType,
    selectedGenreId: Int?,
    selectedYear: YearFilter,
    selectedMinRating: RatingFilter,
    genres: List<Genre>,
    onTypeSelected: (ContentType) -> Unit,
    onGenreSelected: (Int?) -> Unit,
    onYearSelected: (YearFilter) -> Unit,
    onRatingSelected: (RatingFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {

        // Enum.entries ile liste otomatik geliyor
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ContentType.entries) { type ->
                FilterChip(
                    selected = (selectedType == type),
                    onClick = { onTypeSelected(type) },
                    label = { Text(text = type.displayName) }
                )
            }
        }

        if (selectedType != ContentType.ACTORS) {

            if (genres.isNotEmpty()) {
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

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                items(YearFilter.entries) { year ->
                    FilterChip(
                        selected = (selectedYear == year),
                        onClick = { onYearSelected(year) },
                        label = { Text(text = year.displayName) }
                    )
                }
            }


            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                items(RatingFilter.entries) { rating ->
                    FilterChip(
                        selected = (selectedMinRating == rating),
                        onClick = { onRatingSelected(rating) },
                        label = { Text(text = rating.displayName) }
                    )
                }
            }
        }
    }
}