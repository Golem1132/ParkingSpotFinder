package com.example.parkingspotfinder.screens.mapscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.repository.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: DbRepository): ViewModel() {
    private val _markersList = MutableStateFlow<List<ParkingSpotMarker>>(emptyList())
    val markersList = _markersList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMarkers().distinctUntilChanged()
                .collect() { markers ->
                    if (markers.isNotEmpty()){
                        _markersList.value = markers
                    }

                }
        }
    }

    fun insertNewMarker(marker: ParkingSpotMarker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNewMarker(marker)
        }
    }

}