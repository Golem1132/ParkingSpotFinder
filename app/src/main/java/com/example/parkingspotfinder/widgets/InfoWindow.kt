package com.example.parkingspotfinder.widgets

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.data.ParkingSpotType
import com.example.parkingspotfinder.utils.wrap
import java.util.*

@Composable
fun InfoWindow(
        item: ParkingSpotMarker
) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(20))
                    .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = when(item.type) {
                                            ParkingSpotType.BIKES -> painterResource(id = com.example.parkingspotfinder.R.drawable.pedal_bike_48px)
                                            ParkingSpotType.CARS ->  painterResource(id = com.example.parkingspotfinder.R.drawable.directions_car_48px)
                                            }, contentDescription = "", tint = Color.Black)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        Text(text = item.name.wrap(25))
                        Text(text = item.description.wrap(25))
                        Text(text = SimpleDateFormat.getDateTimeInstance().format(Date(item.uploadTime)))
                    }
                }
                Row {
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(10.dp)
                            .background(
                                color = Color.White,
                                shape = CutCornerShape(
                                    bottomStartPercent = 100,
                                    bottomEndPercent = 100
                                )
                            )
                    )
                }
            }
    }
    
}