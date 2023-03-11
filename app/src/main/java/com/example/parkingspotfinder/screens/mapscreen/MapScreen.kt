package com.example.parkingspotfinder.screens.mapscreen

import android.content.Context
import android.location.Address
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
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
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
    val markersList = viewModel.markersList.collectAsState().value
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

        if (showDialog) {
            ShowDialog(
                onDismiss = {
                    showDialog = !showDialog
                }
            ) { name, description, type ->
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
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(text = it.name)
            Text(text = it.description)
            Text(text = Date(it.uploadTime).toString())
        }
    }

}

@Preview
@Composable
fun ShowDialog(
    onDismiss: () -> Unit = {},
    onSubmit: (String, String, String) -> Unit = { _, _, _ -> }
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
                        onSubmit(name.value, description.value, type)

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