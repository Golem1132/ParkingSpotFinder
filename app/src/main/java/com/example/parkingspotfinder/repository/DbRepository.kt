package com.example.parkingspotfinder.repository

import com.example.parkingspotfinder.data.ParkingSpotMarker
import com.example.parkingspotfinder.database.MarkersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbRepository @Inject constructor(private val markersDao: MarkersDao) {

    fun getAllMarkers(): Flow<List<ParkingSpotMarker>> = markersDao.getAllMarkers()

    suspend fun insertNewMarker(marker: ParkingSpotMarker) = markersDao.insertNewMarker(marker)

    suspend fun deleteMarker(marker: ParkingSpotMarker) = markersDao.deleteMarker(marker)
}