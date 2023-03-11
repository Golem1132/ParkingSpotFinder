package com.example.parkingspotfinder.screens.mapscreen

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.repository.DbRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: DbRepository, private val geocoder: Geocoder): ViewModel() {
    private val _markersList = MutableStateFlow<List<ParkingSpotMarker>>(emptyList())
    val markersList = _markersList

    init {
        viewModelScope.launch(Dispatchers.IO) {
                    repository.getAllMarkers().distinctUntilChanged()
                        .collect() { markers ->
                                _markersList.value = markers
                        }
        }

    }

    fun insertNewMarker(marker: ParkingSpotMarker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNewMarker(marker)
        }
    }

    fun deleteMarker(marker: ParkingSpotMarker) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMarker(marker)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFullAddress(latLng: LatLng): Deferred<Address?> {
        val job = viewModelScope.async {
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)?.let {
                if(it.isNotEmpty())
                it[0]
                else null
            }
        }
        job.invokeOnCompletion {
            try {
                if (it == null) {
                    job.getCompleted()
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }

        return job

    }


}