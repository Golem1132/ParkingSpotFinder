package com.example.parkingspotfinder.bottomappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ParkingSpotFinderBottomAppBar(
onDrawerClick: () -> Unit = {}
){
    BottomAppBar(
        cutoutShape = CircleShape
    ) {
        BottomNavigationItem(selected = false,
            onClick = { onDrawerClick() },
            icon = {
                Image(imageVector = Icons.Default.Menu, contentDescription = "Drawer open/close")
            }
        )

    }

}