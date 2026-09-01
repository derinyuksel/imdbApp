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
    genres: List<Genre>,
    onTypeSelected: (String) -> Unit,
    onGenreSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf("All", "Movies", "TV Series", "Actors")

    Column(modifier = modifier.padding(vertical = 8.dp)) {

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(types) { type ->
                FilterChip(
                    selected = (selectedType == type),
                    onClick = { onTypeSelected(type) },
                    label = {Text (text= type) }
                )
            }
        }

        if (selectedType != "Actors" && genres.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {

                item{
                    FilterChip(
                        selected = (selectedGenreId == null),
                        onClick = {onGenreSelected(null)},
                        label = {Text(text = "All Genres") }
                    )
                }

                items(genres) { genre ->
                    FilterChip(
                        selected = (selectedGenreId == genre.id),
                        onClick = {onGenreSelected(genre.id) },
                        label = {Text (text = genre.name)}
                    )
                }
            }

        }
    }



}