package com.example.parkingspotfinder.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.parkingspotfinder.ParkingSpotFinderApp
import com.example.parkingspotfinder.database.MarkersDao
import com.example.parkingspotfinder.database.ParkingSpotFinderDb
import com.example.parkingspotfinder.location.LocationClient
import com.example.parkingspotfinder.location.ParkingSpotFinderLocationClient
import com.example.parkingspotfinder.repository.DbRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ParkingSpotFinderModule {

    @Provides
    @Singleton
    fun provideRepository(markersDao: MarkersDao): DbRepository = DbRepository(markersDao)

    @Provides
    @Singleton
    fun provideMarkersDao(db: ParkingSpotFinderDb): MarkersDao = db.markersDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ParkingSpotFinderDb = Room.databaseBuilder(
        context,
        ParkingSpotFinderDb::class.java,
        "ParkingSpotFinderDb"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context, client: FusedLocationProviderClient): LocationClient = ParkingSpotFinderLocationClient(
        context,
        client
    )

}