package com.example.imdbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.imdbapp.core.ThemeViewModel
import com.example.imdbapp.details.MovieDetailScreen
import com.example.imdbapp.details.PersonDetailScreen
import com.example.imdbapp.home.HomeScreen
import com.example.imdbapp.navigation.Screen
import com.example.imdbapp.profile.ProfileScreen
import com.example.imdbapp.search.SearchScreen
import com.example.imdbapp.ui.theme.ImdbAppTheme
import com.example.imdbapp.watchlist.WatchlistScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val themeViewModel: ThemeViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

            ImdbAppTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                var selectedTab by remember { mutableStateOf(Screen.Home.route) }
                var homeScrollToTopTrigger by remember { mutableIntStateOf(0) }

                LaunchedEffect(navBackStackEntry) {
                    val route = navBackStackEntry?.destination?.route
                    if (route in listOf(Screen.Home.route, Screen.Search.route, Screen.Profile.route)) {
                        selectedTab = route!!
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            FloatingNavbar(
                                selectedTab = selectedTab,
                                onTabClick = { route ->
                                    if (selectedTab == route) {
                                        if (currentRoute == route && route == Screen.Home.route) {
                                            homeScrollToTopTrigger++
                                        } else {
                                            navController.popBackStack(route, inclusive = false)
                                        }
                                    } else {
                                        selectedTab = route
                                        navController.navigate(route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                scrollToTopTrigger = homeScrollToTopTrigger,
                                onMovieClick = { id -> navController.navigate(Screen.MovieDetail.createRoute(id)) },
                                onPersonClick = { id -> navController.navigate(Screen.PersonDetail.createRoute(id)) }
                            )
                        }
                        composable(route = Screen.Search.route) {
                            SearchScreen(onMovieClick = { id -> navController.navigate(Screen.MovieDetail.createRoute(id)) })
                        }
                        composable(route = Screen.Profile.route) {
                            ProfileScreen(
                                onMovieClick = { id ->
                                    navController.navigate(Screen.MovieDetail.createRoute(id))
                                },
                                onWatchlistClick = {
                                    navController.navigate(Screen.Watchlist.route)
                                }
                            )
                        }
                        composable(route = Screen.MovieDetail.route) {
                            MovieDetailScreen(
                                onBackClick = { navController.popBackStack() },
                                onActorClick = { id -> navController.navigate(Screen.PersonDetail.createRoute(id)) }
                            )
                        }
                        composable(route = Screen.PersonDetail.route) {
                            PersonDetailScreen(
                                onBackClick = { navController.popBackStack() },
                                onMovieClick = { id -> navController.navigate(Screen.MovieDetail.createRoute(id)) }
                            )
                        }

                        composable(route = Screen.Watchlist.route) {
                            WatchlistScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

//NAVBAR
@Composable
fun FloatingNavbar(
    selectedTab: String,
    onTabClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier.width(220.dp).height(64.dp),
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        tonalElevation = 8.dp,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavbarItem(
                icon = Icons.Default.Home,
                isSelected = selectedTab == Screen.Home.route,
                onClick = { onTabClick(Screen.Home.route) }
            )
            NavbarItem(
                icon = Icons.Default.Search,
                isSelected = selectedTab == Screen.Search.route,
                onClick = { onTabClick(Screen.Search.route) }
            )
            NavbarItem(
                icon = Icons.Default.Person,
                isSelected = selectedTab == Screen.Profile.route,
                onClick = { onTabClick(Screen.Profile.route) }
            )
        }
    }
}

@Composable
fun NavbarItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.size(if (isSelected) 28.dp else 24.dp)
        )
    }
}