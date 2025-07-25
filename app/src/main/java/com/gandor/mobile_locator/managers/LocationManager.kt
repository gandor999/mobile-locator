package com.gandor.mobile_locator.managers

import android.annotation.SuppressLint
import android.util.Log
import com.gandor.mobile_locator.MainActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationManager(private val mainActivity: MainActivity) {
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Pair<String, String> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        Log.d("GEO TEST", "getLastLocation")

        if (!PermissionManager.isCoarseLocationGranted() && !PermissionManager.isFineLocationGranted()) {
            if (!PermissionManager.isNotAllowedToAskAgain()) {
                PermissionManager.promptRequiredPermissions()
            }

            return Pair("", "")
        }

        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val lon = location.longitude
                Log.d("GEO TEST", "Lat: $lat, Lon: $lon")
            } else {
                Log.d("GEO TEST", "Location is null (GPS may be off or no prior fix)")
            }
        }.addOnFailureListener {
            Log.e("GEO TEST", "Failed to get location", it)
        }

        return Pair("", "")
    }
}