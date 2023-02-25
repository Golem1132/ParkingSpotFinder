package com.example.parkingspotfinder.data

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector

data class AppBarItem(
    val id: Int,
    val title: String,
    val route: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
