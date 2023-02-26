package com.example.parkingspotfinder.screens.mapscreen

import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

fun getMapUiSettings(): MapUiSettings = MapUiSettings(
    compassEnabled = true,
    indoorLevelPickerEnabled = false,
    mapToolbarEnabled = true,
    myLocationButtonEnabled = true,
    rotationGesturesEnabled = true,
    scrollGesturesEnabled = true,
    scrollGesturesEnabledDuringRotateOrZoom = true,
    tiltGesturesEnabled = false,
    zoomControlsEnabled = false,

)

fun getMapProperties() : MapProperties = MapProperties(
    isMyLocationEnabled = true,
    isTrafficEnabled = true
)