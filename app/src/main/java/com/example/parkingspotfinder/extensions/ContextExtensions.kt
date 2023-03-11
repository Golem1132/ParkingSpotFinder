package com.example.parkingspotfinder.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

fun Context.checkLocationPermission(): Boolean {
    return (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
}