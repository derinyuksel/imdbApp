package com.example.imdbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.imdbapp.watchlist.WatchlistScreen
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imdbapp.core.ThemeViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val themeViewModel: ThemeViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

            ImdbAppTheme (darkTheme = isDarkMode) {

                var selectedTab by remember { mutableStateOf(Screen.Home.route) }

                var homeScrollToTopTrigger by remember { mutableIntStateOf(0) }

                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                LaunchedEffect(navBackStackEntry) {
                    val route = navBackStackEntry?.destination?.route
                    if (route in listOf(Screen.Home.route, Screen.Search.route, Screen.Watchlist.route, Screen.Settings.route)) {
                        selectedTab = route!!
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {


                        if (currentRoute in listOf(
                                Screen.Home.route,
                                Screen.Search.route,
                                Screen.Watchlist.route,
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
                                        if (selectedTab == Screen.Home.route) {
                                            if (currentRoute == Screen.Home.route) { //Zaten ana ekrandaysa -> başa kaydır
                                                homeScrollToTopTrigger++
                                            } else { //Detay sayfasındaysa ana ekrana geri don
                                                navController.popBackStack(Screen.Home.route, inclusive = false)
                                            }
                                        } else { //Baska sekmeden geliyorsa normal
                                            selectedTab = Screen.Home.route
                                            navController.navigate(Screen.Home.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home") }
                                )

                                // Search Tab
                                NavigationBarItem(
                                    selected = (selectedTab == Screen.Search.route),
                                    onClick = {
                                        if (selectedTab == Screen.Search.route) {
                                            if (currentRoute != Screen.Search.route) {
                                                navController.popBackStack(Screen.Search.route, inclusive = false)
                                            }
                                        } else {
                                            selectedTab = Screen.Search.route
                                            navController.navigate(Screen.Search.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                                    label = { Text("Search") }
                                )

                                // Watchlist Tab
                                NavigationBarItem(
                                    selected = (selectedTab == Screen.Watchlist.route),
                                    onClick = {
                                        if (selectedTab == Screen.Watchlist.route) {
                                            if (currentRoute != Screen.Watchlist.route) {
                                                navController.popBackStack(Screen.Watchlist.route, inclusive = false)
                                            }
                                        } else {
                                            selectedTab = Screen.Watchlist.route
                                            navController.navigate(Screen.Watchlist.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Watchlist") },
                                    label = { Text("Watchlist") }
                                )

                                // Settings Tab
                                NavigationBarItem(
                                    selected = (selectedTab == Screen.Settings.route),
                                    onClick = {
                                        if (selectedTab == Screen.Settings.route) {
                                            if (currentRoute != Screen.Settings.route) {
                                                navController.popBackStack(Screen.Settings.route, inclusive = false)
                                            }
                                        } else {
                                            selectedTab = Screen.Settings.route
                                            navController.navigate(Screen.Settings.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
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
                                scrollToTopTrigger = homeScrollToTopTrigger,
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

                        // Watchlist Route
                        composable(route = Screen.Watchlist.route)  {
                            WatchlistScreen(
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
