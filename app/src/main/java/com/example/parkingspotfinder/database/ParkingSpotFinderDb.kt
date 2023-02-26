package com.example.parkingspotfinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.parkingspotfinder.data.ParkingSpotMarker

@Database(entities = [
ParkingSpotMarker::class
],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ParkingSpotFinderDb: RoomDatabase() {

    abstract fun markersDao(): MarkersDao


}