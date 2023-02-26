package com.example.parkingspotfinder.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdate(interval: Long): Flow<Location>

    class LocationException(msg: String): Exception()
}