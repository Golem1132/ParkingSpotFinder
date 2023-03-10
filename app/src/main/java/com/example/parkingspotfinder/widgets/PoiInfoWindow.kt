package com.example.parkingspotfinder.widgets

import android.location.Address
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PoiInfoWindow(
    address: Address?,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            onDismiss()
        }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            Column(modifier = Modifier.fillMaxWidth()
                .padding(10.dp)) {
                Text(text = "${address?.thoroughfare} ${address?.featureName}")
                Text(text = "${address?.postalCode} ${address?.locality}")
                Text(text = "${address?.countryName}")
            }

        }

    }
}