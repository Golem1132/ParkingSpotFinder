package com.example.parkingspotfinder.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubmitButton(
    text: String,
    enabled: Boolean = false,
    onClick: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(0.7f),
        enabled = enabled,
        shape = RoundedCornerShape(50),
        onClick = { onClick.invoke() }) {
        Text(text = text)
    }
}