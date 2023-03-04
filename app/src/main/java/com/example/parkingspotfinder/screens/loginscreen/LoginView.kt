package com.example.parkingspotfinder.screens.loginscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingspotfinder.widgets.BannerBox
import com.example.parkingspotfinder.widgets.InputField
import com.example.parkingspotfinder.widgets.SubmitButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginView(
    onClick: () -> Unit,
    onSubmit: (String, String) -> Unit
) {

    val loginState = rememberSaveable {
        mutableStateOf("")
    }
    val passwordState = rememberSaveable {
        mutableStateOf("")
    }
    val buttonEnabled = remember(loginState.value, passwordState.value) {
        mutableStateOf(loginState.value.length >= 3 && passwordState.value.length >= 8)
    }
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BannerBox(
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                    fontSize = 30.sp,
                    color = Color.Black
                )
                ) {
                    append("Parking")
                }
                withStyle(
                    SpanStyle(
                    fontSize = 20.sp,
                    color = Color.Green
                )
                ) {
                    append("Spot")
                }
                withStyle(
                    SpanStyle(
                    fontSize = 30.sp,
                    color = Color.Blue
                )
                ) {
                    append("Finder")
                }
            }
        )
        InputField(
            value = loginState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = VisualTransformation.None
        ) {
            Text(text = "Login")
        }
        InputField(
            value = passwordState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboard?.hide()
                }
            )
        ) {
            Text(text = "Password")
        }
        Spacer(modifier = Modifier.height(10.dp))
        SubmitButton(
            text = "Login",
            enabled = buttonEnabled.value
        ) {
            onSubmit(loginState.value, passwordState.value)
        }
        Spacer(modifier = Modifier.height(10.dp))
        RegisterBox(){
            onClick()

        }
    }

}