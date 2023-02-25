package com.example.parkingspotfinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.screens.MapScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MapScreen.route) {
        composable(route = Screens.MapScreen.route) {
            MapScreen(navController = navController)
        }

    }
}