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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingspotfinder.widgets.BannerBox
import com.example.parkingspotfinder.widgets.InputField
import com.example.parkingspotfinder.widgets.SubmitButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterView(
    onSubmit: (String, String, String) -> Unit
) {
    val emailRegex = Regex("\\w+@\\w*\\.\\w+")
    val fullNameState = rememberSaveable {
        mutableStateOf("")
    }
    val passwordState = rememberSaveable {
        mutableStateOf("")
    }
    val repeatPasswordState = rememberSaveable {
        mutableStateOf("")
    }
    val emailState = rememberSaveable {
        mutableStateOf("")
    }
    val buttonEnabled = remember(emailState.value, fullNameState.value, passwordState.value, repeatPasswordState.value) {
        mutableStateOf(fullNameState.value.length >= 3 && passwordState.value.length >= 8 && passwordState.value == repeatPasswordState.value && emailRegex.matches(emailState.value))
    }
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BannerBox(buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 30.sp,
                    color = Color.Black
                )
            ) {
                append("Create an account")
            }
        })
        InputField(
            value = emailState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = VisualTransformation.None
        ) {
            Text(text = "E-mail")
        }

        InputField(
            value = fullNameState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = VisualTransformation.None
        ) {
            Text(text = "Full name")
        }
        InputField(
            value = passwordState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions.Default
        ) {
            Text(text = "Password")
        }
        InputField(
            value = repeatPasswordState,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboard?.hide()
                }
            )
        ) {
            Text(text = "Repeat password")
        }
        Spacer(modifier = Modifier.height(40.dp))
        SubmitButton(
            text = "Register",
            enabled = buttonEnabled.value
        ) {
            onSubmit(emailState.value, passwordState.value, fullNameState.value)
        }

    }

}

