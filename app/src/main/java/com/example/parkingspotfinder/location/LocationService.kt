package com.example.parkingspotfinder.location

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService: Service() {
    companion object {
        var position = LatLng(0.0, 0.0)
    }
        private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private lateinit var locationClient: LocationClient


    override fun onCreate() {
        super.onCreate()
        locationClient = ParkingSpotFinderLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        locationClient.getLocationUpdate(5000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach {
            position = LatLng(it.latitude, it.longitude)

            }.launchIn(serviceScope)

    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}