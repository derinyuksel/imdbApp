package com.example.imdbapp

import android.net.http.SslCertificate.restoreState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.imdbapp.home.HomeScreen
import com.example.imdbapp.home.HomeScreenContent
import com.example.imdbapp.ui.theme.ImdbAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.imdbapp.home.HomeUiState
import com.example.imdbapp.navigation.Screen
import androidx.navigation.compose.composable
import com.example.imdbapp.details.MovieDetailScreen
import com.example.imdbapp.details.PersonDetailScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.imdbapp.search.SearchScreen
import com.example.imdbapp.settings.SettingsScreen
import androidx.navigation.NavGraph.Companion.findStartDestination


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImdbAppTheme {
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {


                        if (currentRoute in listOf(
                                Screen.Home.route,
                                Screen.Search.route,
                                Screen.Settings.route,
                                Screen.MovieDetail.route,
                                Screen.PersonDetail.route,
                            )
                        ) {
                            NavigationBar {
                                // Home Tab
                                NavigationBarItem(
                                    selected = (currentRoute == Screen.Home.route),
                                    onClick = {
                                        if (!navController.popBackStack(
                                                Screen.Home.route,
                                                inclusive = false
                                            )
                                        ) {
                                            navController.navigate(Screen.Home.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = "Home"
                                        )
                                    },
                                    label = { Text("Home") }
                                )

                                // Search Tab
                                NavigationBarItem(
                                    selected = (currentRoute == Screen.Search.route),
                                    onClick = {
                                        if (navController.popBackStack(
                                                Screen.Home.route,
                                                inclusive = false
                                            )
                                        )
                                            navController.navigate(Screen.Search.route) {
                                                popUpTo(navController.graph.findStartDestination().id)
                                                { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true

                                            }
                                    },
                                    icon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = "Search"
                                        )
                                    },
                                    label = { Text("Search") }
                                )

                                // Settings Tab
                                NavigationBarItem(
                                    selected = (currentRoute == Screen.Settings.route),
                                    onClick = {
                                        if (!navController.popBackStack(
                                                Screen.Settings.route,
                                                inclusive = false
                                            )
                                        ) {
                                            navController.navigate(Screen.Settings.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            Icons.Default.Settings,
                                            contentDescription = "Settings"
                                        )
                                    },
                                    label = { Text("Settings") }
                                )
                            }
                        }
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Home Route
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                },
                                onPersonClick = { personId ->
                                    navController.navigate(Screen.PersonDetail.createRoute(personId))
                                }
                            )
                        }

                        // Search Route
                        composable(route = Screen.Search.route) {
                            SearchScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                }
                            )
                        }

                        // Settings Route
                        composable(route = Screen.Settings.route) {
                            SettingsScreen()
                        }

                        // Movie Detail Route
                        composable(route = Screen.MovieDetail.route) {
                            MovieDetailScreen(
                                onBackClick = { navController.popBackStack() },
                                onActorClick = { personId ->
                                    navController.navigate(Screen.PersonDetail.createRoute(personId))
                                }
                            )
                        }

                        // Person Detail Route
                        composable(route = Screen.PersonDetail.route) {
                            PersonDetailScreen(
                                onBackClick = { navController.popBackStack() },
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                }
                            )
                        }
                    }


                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ImdbAppTheme {
        HomeScreenContent(
            state = HomeUiState(isLoading = true),
            modifier = Modifier,
            onMovieClick = {},
            onPersonClick = {}
        )
    }
}
