package com.example.imdbapp.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth().padding(vertical = 8.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ContentType.entries) { type ->
                    val isSelected = selectedType == type
                    TextButton(
                        onClick = { onTypeSelected(type) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = type.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }


            if (selectedType != ContentType.ACTORS) {
                TextButton(
                    onClick = { isExpanded = !isExpanded },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Filters", style = MaterialTheme.typography.labelLarge)
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isExpanded && selectedType != ContentType.ACTORS,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(modifier = Modifier.padding(top = 16.dp)) {

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)


                SleekFilterRow(title = "Genre") {
                    item {
                        SleekChip("All", selectedGenreId == null) { onGenreSelected(null) }
                    }
                    items(genres) { genre ->
                        SleekChip(genre.name, selectedGenreId == genre.id) { onGenreSelected(genre.id) }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)


                SleekFilterRow(title = "Year") {
                    items(YearFilter.entries) { year ->
                        SleekChip(year.displayName, selectedYear == year) { onYearSelected(year) }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)


                SleekFilterRow(title = "Rating") {
                    items(RatingFilter.entries) { rating ->
                        SleekChip(rating.displayName, selectedMinRating == rating) { onRatingSelected(rating) }
                    }
                }
            }
        }
    }
}

@Composable
fun SleekFilterRow(title: String, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 16.dp, end = 24.dp).width(60.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 16.dp),
            content = content
        )
    }
}

@Composable
fun SleekChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
        )
    }
}