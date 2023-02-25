package com.example.parkingspotfinder.widgets

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ParkingSpotFinderFAB(
    backgroundColor: Color,
    icon: ImageVector,
    iconColor: Color,
    onClick:() -> Unit
){
    FloatingActionButton(
        backgroundColor = backgroundColor,
        onClick = { onClick.invoke() }) {
        Image(imageVector = icon, contentDescription = "Add marker",
        colorFilter = ColorFilter.tint(iconColor))

    }
}