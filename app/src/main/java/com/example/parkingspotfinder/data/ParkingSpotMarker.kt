package com.example.parkingspotfinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "Markers_tbl")
data class ParkingSpotMarker(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "My marker",
    val latLng: LatLng

)
