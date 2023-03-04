package com.example.parkingspotfinder.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ParkingSpotFinderTopAppBar(
    onIconClick: () -> Unit
) {
    TopAppBar {
        Icon(modifier = Modifier.clickable { onIconClick() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go back")
    }
}