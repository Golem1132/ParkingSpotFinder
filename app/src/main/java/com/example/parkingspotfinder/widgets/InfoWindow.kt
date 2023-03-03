package com.example.parkingspotfinder.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Preview
@Composable
fun InfoWindow(
    title: String = "Test title",
    icon: ImageVector = Icons.Default.Info
) {
    Box(modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(10))) {
        Row() {
            Icon(imageVector = icon, contentDescription = "")
            Column() {
                Text(text = title)
                Text(text = title)
            }

        }
    }
    
}