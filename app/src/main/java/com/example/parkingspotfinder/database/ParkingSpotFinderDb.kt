package com.example.parkingspotfinder.database

import androidx.room.*
import com.example.parkingspotfinder.data.ParkingSpotMarker

@Database(entities = [
ParkingSpotMarker::class
],
    version = 1,
    exportSchema = true,
    autoMigrations = [
    ]
)
@TypeConverters(Converters::class)
abstract class ParkingSpotFinderDb: RoomDatabase() {

    abstract fun markersDao(): MarkersDao


}