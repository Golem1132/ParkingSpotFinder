package com.example.parkingspotfinder.database

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder

class Converters {

    @TypeConverter
    fun fromLatLng(latLng: LatLng): String {
    return GsonBuilder().create().toJson(latLng)
    }

    @TypeConverter
    fun toLatLng(string: String): LatLng {
        return GsonBuilder().create().fromJson(string, LatLng::class.java)
    }
}