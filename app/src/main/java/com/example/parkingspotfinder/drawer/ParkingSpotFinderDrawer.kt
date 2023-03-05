package com.example.parkingspotfinder.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ParkingSpotFinderDrawer(
    onLogoutClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        UserCard(
            fullName = "Tester Testovsky",
            email = "test@test.com"
        ){
            onLogoutClick()
        }
        DrawerRow()
        DrawerRow()
        DrawerRow()

    }

}
@Preview
@Composable
fun DrawerRow() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(color = MaterialTheme.colors.background)
        .padding(horizontal = 20.dp)
        .clickable { },
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically) {
        Text(text = "CatName")
        Icon(imageVector = Icons.Default.Search, contentDescription = "Category icon")

    }
    Divider(color = MaterialTheme.colors.onBackground, thickness = 2.dp, startIndent = 10.dp)

}

@Composable
fun UserCard(
    fullName: String,
    email: String,
    image: Any? = null,
    onLogoutClick: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        Column() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)) {
                Column(modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.5f)
                ) {
                    Image(modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Default.Person, contentDescription = "User photo")
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Hi, ${fullName}",
                    style = MaterialTheme.typography.h5)
                    Text(text = email)
                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.End) {
                IconButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = { onLogoutClick() }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Logout")
                        Image(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            }
        }

    }

}
