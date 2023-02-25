package com.example.parkingspotfinder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.bottomappbar.ParkingSpotFinderBottomAppBar
import com.example.parkingspotfinder.widgets.ParkingSpotFinderFAB
import com.google.maps.android.compose.GoogleMap

@Preview
@Composable
fun MapScreen(
    navController: NavController = rememberNavController()
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            ParkingSpotFinderBottomAppBar(
                navController = navController,
                backgroundColor = Color.Green,
                contentColor = Color.White,
                cutoutShape = CircleShape
            )
        },
        floatingActionButton = {
        ParkingSpotFinderFAB(backgroundColor = Color.Blue,
            icon = Icons.Sharp.Add,
            iconColor = Color.White) {

        }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { it
        GoogleMap(
            modifier = Modifier.fillMaxSize()
        )
    }

}