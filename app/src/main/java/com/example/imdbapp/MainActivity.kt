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
                            HomeScreen() //Define what home destination is
                        }


                        composable(route = Screen.MovieDetail.route) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            Text(text = "Movie Detail for ID: $movieId")

                        }

                        composable(route = Screen.PersonDetail.route) { backStackEntry ->
                            val personId = backStackEntry.arguments?.getString("personId")
                            Text(text = "Person Detail for ID: $personId")

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
                state = HomeUiState(isLoading = true), modifier = Modifier
            )
        }
    }
}

