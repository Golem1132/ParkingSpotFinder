package com.example.parkingspotfinder.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.screens.loginscreen.LoginScreen
import com.example.parkingspotfinder.screens.mapscreen.MapScreen
import com.example.parkingspotfinder.screens.mapscreen.MapViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(route = Screens.MapScreen.route) {
            val viewModel = hiltViewModel<MapViewModel>()
            MapScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navController)
        }


    }
}