package com.example.parkingspotfinder.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ParkingSpotFinderTopAppBar(
    isNavIcon: Boolean,
    onIconClick: () -> Unit = {}
) {
    TopAppBar {
        Icon(modifier = Modifier.clickable { onIconClick() },
            imageVector = if(isNavIcon)
                Icons.Default.ArrowBack
            else
                Icons.Default.Menu,
            contentDescription = "Go back")
    }
}