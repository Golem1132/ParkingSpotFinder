package com.example.parkingspotfinder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.parkingspotfinder.data.ParkingSpotMarker
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkersDao {

    @Query("SELECT * FROM Markers_tbl")
    fun getAllMarkers(): Flow<List<ParkingSpotMarker>>

    @Insert
    suspend fun insertNewMarker(marker: ParkingSpotMarker)

    @Delete
    suspend fun deleteMarker(marker: ParkingSpotMarker)

}