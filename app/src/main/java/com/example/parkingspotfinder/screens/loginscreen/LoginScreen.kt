package com.example.parkingspotfinder.screens.loginscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parkingspotfinder.navigation.Screens
import com.example.parkingspotfinder.topappbar.ParkingSpotFinderTopAppBar
import com.example.parkingspotfinder.widgets.BannerBox
import com.example.parkingspotfinder.widgets.InputField
import com.example.parkingspotfinder.widgets.SubmitButton


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(LoginViewModel::class.java)
) {
    var inLoginMode by remember {
        mutableStateOf(true)
    }
    Scaffold(topBar = {
        if (!inLoginMode)
            ParkingSpotFinderTopAppBar(){
                inLoginMode = true
            }
        else
            Box(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth())

    }) {it
        if (inLoginMode) {
            LoginView(
                onClick = {
                    inLoginMode = !inLoginMode
                }
            ) { email, password ->
                loginViewModel.logIn(email, password) {
                    navController.navigate(Screens.MapScreen.route)
                }
            }
        }

        else {
            RegisterView() { email, password, fullName ->
                loginViewModel.registerNewUser(email, password, fullName) {
                    loginViewModel.logIn(email, password) {
                        navController.navigate(Screens.MapScreen.route)
                    }
                }
            }
        }
    }



}







@Composable
fun RegisterBox(
    onClick: () -> Unit
) {
    Row(){
    Text(text = "Need account? ")
    Text(modifier = Modifier.clickable {
                                       onClick.invoke()
    },
        text = "Sign up!", color = Color.Blue)
    }

}