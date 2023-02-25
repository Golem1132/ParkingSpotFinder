package com.example.parkingspotfinder.bottomappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.parkingspotfinder.data.AppBarItem
import com.example.parkingspotfinder.navigation.Screens

@Composable
fun ParkingSpotFinderBottomAppBar(
    navController: NavController,
    backgroundColor: Color,
    cutoutShape: Shape,
    contentColor: Color,
appBarItemsList: List<AppBarItem> = listOf(
    AppBarItem(
        1,
        "Map",
        Screens.MapScreen.route,
        Icons.Default.Home,
        {}
    ),
    AppBarItem(
        1,
        "Favourites",
        "",
        Icons.Default.Favorite,
        {}
    )
)
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomAppBar(
        backgroundColor = backgroundColor,
        cutoutShape = cutoutShape,
    ) {
            appBarItemsList.forEach {
                val selected = it.route == backStackEntry.value?.destination?.route
                    BottomNavigationItem(selected = selected,
                        onClick = {
                            it.onClick.invoke()
                                  },
                icon = {
                    Image(imageVector = it.icon,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(contentColor))
                },
                label = {
                    Text(text = it.title,
                    color = contentColor)
                })

            }

    }
}