package com.example.parkingspotfinder.screens.mapscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.bottomappbar.ParkingSpotFinderBottomAppBar
import com.example.parkingspotfinder.location.LocationService
import com.example.parkingspotfinder.widgets.ParkingSpotFinderFAB
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*

@Preview
@Composable
fun MapScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel = MapViewModel()
    viewModel

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            ParkingSpotFinderBottomAppBar(
                navController = navController,
                backgroundColor = Color.DarkGray,
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
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = getMapUiSettings(),
                properties = getMapProperties()
            ) {

            }

        }

    }

}