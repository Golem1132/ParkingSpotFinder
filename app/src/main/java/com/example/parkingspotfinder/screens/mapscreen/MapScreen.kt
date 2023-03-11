package com.example.parkingspotfinder.screens.mapscreen

import android.content.Context
import android.location.Address
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.parkingspotfinder.R
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.data.ParkingSpotType
import com.example.parkingspotfinder.extensions.checkLocationPermission
import com.example.parkingspotfinder.location.LocationService
import com.example.parkingspotfinder.widgets.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    context: Context
) {
    var filterState by remember {
        mutableStateOf(0)
    }
    val markersList = viewModel.markersList.collectAsState().value.filter {
        when(filterState) {
            1 -> it.type.name == ParkingSpotType.CARS.name
            2 -> it.type.name == ParkingSpotType.BIKES.name
            else -> it.type.name == it.type.name
        }
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var pos = LatLng(0.0, 0.0)
    val cameraPosition = rememberCameraPositionState()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    var poiInfoWindowState by remember {
        mutableStateOf(false)
    }
    val poiState = rememberMarkerState()
    val addressState = remember<MutableState<Address?>> {
        mutableStateOf(null)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ParkingSpotFinderFAB(icon = Icons.Sharp.Add) {
                if (LocationService.isPermissionGranted.value) {
                    pos = LocationService.position
                    showDialog = true
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = getMapUiSettings(),
                properties = MapProperties(
                    isMyLocationEnabled = LocationService.isPermissionGranted.value
                ),
                cameraPositionState = cameraPosition,
                onMapClick = { poi ->
                    viewModel.getFullAddress(poi).getCompleted().let { address ->
                        if (address != null) {
                            poiInfoWindowState = true
                            addressState.value = address
                            poiState.position = poi
                        }
                        else {
                            Toast.makeText(context, "Can't find the address", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onMapLoaded = {
                    if (LocationService.isPermissionGranted.value)
                        scope.launch(Dispatchers.Main) {
                            cameraPosition.animate(
                                CameraUpdateFactory.newCameraPosition(
                                    CameraPosition.fromLatLngZoom(LocationService.position, 15f)
                                )
                            )
                        }
                },
                onMapLongClick = {
                    pos = it
                    showDialog = !showDialog
                }
            ) {
                markersList.forEach { marker ->
                    MarkerInfoWindow(
                        state = MarkerState(marker.latLng),
                        title = marker.name
                    ) {
                        InfoWindow(marker)
                    }
                }

                if (poiInfoWindowState)
                    Marker(
                        state = poiState
                    )
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom) {
            Button(onClick = {
                             filterState += 1
                if(filterState > 2) filterState = 0
            }, shape = CircleShape) {
                val painter = when (filterState) {
                    1 -> painterResource(id = R.drawable.directions_car_48px)
                    2 -> painterResource(id = R.drawable.pedal_bike_48px)
                    else -> painterResource(id = R.drawable.done_all_48px)
                }
                    Image(painter = painter, contentDescription = "Filter options")

            }
        }

        if (showDialog) {
            ShowDialog(
                onDismiss = {
                    showDialog = !showDialog
                }
            ) { name, description, type, error ->
                if (error) {
                Toast.makeText(context, "Add more information", Toast.LENGTH_SHORT).show()
                }
                    else {
                    viewModel.insertNewMarker(
                        ParkingSpotMarker(
                            name = name,
                            description = description,
                            latLng = pos,
                            type = ParkingSpotType.valueOf(type),
                            uploadTime = Date.from(Instant.now()).time
                        )
                    )
                    showDialog = !showDialog
                }
            }
        } else {
            Box {}
        }

        if (poiInfoWindowState)
            PoiInfoWindow(address = addressState.value) {
                poiInfoWindowState = false
            }
        else Box {}
    }
    DraggableDrawer(list = markersList,
        buttonsRow = {
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    scope.launch(Dispatchers.Main) {
                        cameraPosition.animate(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(it.latLng, 15f)
                            )
                        )
                    }
                },
                shape = CircleShape
            ) {
                Image(imageVector = Icons.Default.Search, contentDescription = "ZoomTo")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    viewModel.deleteMarker(it)
                },
                shape = CircleShape
            ) {
                Image(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
            Spacer(modifier = Modifier.width(10.dp))
        }) {
        Column(modifier = Modifier.shadow(2.dp)
            .padding(horizontal = 10.dp)
            ) {
            Text(text = it.name)
            Text(text = it.description)
            Text(text = Date(it.uploadTime).toString())
        }
    }

}


@Composable
fun ShowDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        val name = remember {
            mutableStateOf("")
        }
        val description = remember {
            mutableStateOf("")
        }
        var type by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(10)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                value = name,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                trailingIcon = {}
            ) {
                Text(text = "Name")
            }
            InputField(
                value = description,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                trailingIcon = {}
            ) {
                Text(text = "Description")
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                items(ParkingSpotType.values()) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = item.name == type, onClick = { type = item.name })
                        Text(text = item.desc)
                    }

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    if (name.value.isNotBlank() || type.isNotBlank())
                        onSubmit(name.value, description.value, type,false)
                    else {
                        onSubmit("","","", true)
                    }

                }) {
                    Text(text = "Create")
                }

            }
        }

    }
}

@Composable
fun CheckboxWithLabel(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    label: @Composable () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChanged)
        label()
    }

}