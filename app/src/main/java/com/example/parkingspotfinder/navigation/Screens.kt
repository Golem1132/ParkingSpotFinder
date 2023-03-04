package com.example.parkingspotfinder.navigation

sealed class Screens(val route: String) {
    object MapScreen: Screens("MapScreen")
    object LoginScreen: Screens("LoginScreen")
    object AddMarkerScreen: Screens("AddMarkerScreen")
}
