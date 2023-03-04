package com.example.parkingspotfinder.bottomappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkingspotfinder.data.AppBarItem
import com.example.parkingspotfinder.navigation.Screens

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