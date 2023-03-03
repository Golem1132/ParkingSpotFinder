package com.example.parkingspotfinder.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    label: @Composable () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        label = {
            label.invoke()
        },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        visualTransformation = visualTransformation,
    )

}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    label: @Composable () -> Unit
) {
    var visibility by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        label = {
            label.invoke()
        },
        trailingIcon = {
            IconButton(onClick = {
                visibility = !visibility
            }) {
                Image(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Show/hide password"
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        visualTransformation = if(visibility){
            VisualTransformation.None
        }
        else {
            PasswordVisualTransformation()
        },
    )

}