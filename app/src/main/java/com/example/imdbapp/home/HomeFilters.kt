package com.example.imdbapp.home


enum class ContentType(val displayName: String) {
    ALL("All"),
    MOVIES("Movies"),
    TV_SERIES("TV Series"),
    ACTORS("Actors")
}


enum class YearFilter(val displayName: String) {
    ALL("All Years"),
    Y2020S("2020s"),
    Y2010S("2010s"),
    CLASSICS("Classics")
}


enum class RatingFilter(val displayName: String, val minRating: Double?) {
    ALL("All Ratings", null),
    R8PLUS("8+ ⭐", 8.0),
    R7PLUS("7+ ⭐", 7.0),
    R6PLUS("6+ ⭐", 6.0)
}