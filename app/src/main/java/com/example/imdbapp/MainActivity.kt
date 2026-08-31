package com.example.imdbapp

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


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImdbAppTheme {
                val navController = rememberNavController() //Controller navigation part

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route, //Start on the home screen
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                },
                                onPersonClick = { personId ->
                                    navController.navigate(Screen.PersonDetail.createRoute(personId))
                                }
                            ) //Define what home destination is
                        }


                        composable(route = Screen.MovieDetail.route) { backStackEntry ->
                            MovieDetailScreen(
                                onBackClick = {navController.popBackStack()},
                                onActorClick = { personId ->
                                    navController.navigate(Screen.PersonDetail.createRoute(personId))
                                }
                            )

                        }

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
}

