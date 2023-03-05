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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.bottomappbar.ParkingSpotFinderBottomAppBar
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.drawer.ParkingSpotFinderDrawer
import com.example.parkingspotfinder.location.LocationService
import com.example.parkingspotfinder.navigation.Screens
import com.example.parkingspotfinder.topappbar.ParkingSpotFinderTopAppBar
import com.example.parkingspotfinder.widgets.InfoWindow
import com.example.parkingspotfinder.widgets.ParkingSpotFinderFAB
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navController: NavController
) {
    val markersList = viewModel.markersList.collectAsState().value
    val cameraPosition = rememberCameraPositionState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                 ParkingSpotFinderTopAppBar(false) {
                     if(scaffoldState.drawerState.isOpen) {
                         scope.launch {
                             scaffoldState.drawerState.close()
                         }
                     } else {
                         scope.launch {
                             scaffoldState.drawerState.open()
                         }
                     }
                 }
        },
        drawerContent = {
          ParkingSpotFinderDrawer() {
              FirebaseAuth.getInstance().signOut().run {
                  navController.navigate(Screens.LoginScreen.route)
              }
          }
        },
        drawerGesturesEnabled = false,
        scaffoldState = scaffoldState,
        floatingActionButton = {
        ParkingSpotFinderFAB(icon = Icons.Sharp.Add) {
            viewModel.insertNewMarker(
                ParkingSpotMarker(
                    name = "Fresh marker",
                    latLng = LocationService.position
                )
            )
        }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { it
            Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = getMapUiSettings(),
                properties = getMapProperties(),
                cameraPositionState = cameraPosition,
                onMapLoaded = {
                    scope.launch(Dispatchers.Main) {
                        cameraPosition.animate(
                            CameraUpdateFactory.newCameraPosition(
                            CameraPosition.fromLatLngZoom(LocationService.position, 15f)
                        ))
                    }
                }
            ) {
                markersList.forEach { marker ->
                    MarkerInfoWindow(
                        state = MarkerState(marker.latLng),
                        title = marker.name
                    ){
                        InfoWindow(
                            title = marker.name
                        )
                    }

                }

                }

        }

    }

}