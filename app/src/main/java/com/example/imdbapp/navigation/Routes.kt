package com.example.imdbapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
    object PersonDetail : Screen("person_detail/{personId}") {
        fun createRoute(personId: Int) = "person_detail/$personId"
    }
}