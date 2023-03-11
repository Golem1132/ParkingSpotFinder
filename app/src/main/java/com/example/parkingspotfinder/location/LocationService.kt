package com.example.parkingspotfinder.location

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import com.example.parkingspotfinder.ParkingSpotFinderApp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LocationService: Service() {

    @Inject lateinit var locationClient: LocationClient
    companion object {
        var isPermissionGranted = mutableStateOf(false)
        var position = LatLng(0.0, 0.0)
    }
        private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)



    override fun onCreate() {
        super.onCreate()

        locationClient.getLocationUpdate(5000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach {
            position = LatLng(it.latitude, it.longitude)

            }.launchIn(serviceScope)

        isPermissionGranted.value = true

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isPermissionGranted.value = false
        stopSelf()
    }
}