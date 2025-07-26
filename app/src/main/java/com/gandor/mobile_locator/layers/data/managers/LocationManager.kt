package com.gandor.mobile_locator.layers.data.managers

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationManager() {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(activity: Activity): Location? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        if (!PermissionManager.isAllNeededRequiredPermissionsGranted(activity)) {
            if (!PermissionManager.isNotAllowedToAskAgain(activity)) {
                PermissionManager.promptRequiredPermissions(activity)
            }

            return null
        }

        val cancellationTokenSource = CancellationTokenSource()

        return suspendCancellableCoroutine { cont ->
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener {
                if (it != null) {
                    val lat = it.latitude
                    val lon = it.longitude
                    Log.d("GEO TEST", "Lat: $lat, Lon: $lon")
                } else {
                    Log.d("GEO TEST", "Location is null (GPS may be off or no prior fix)")
                }

                cont.resume(it)

            }.addOnFailureListener {
                Log.e("GEO TEST", "Failed to get location", it)
                cont.resumeWithException(it)
            }
        }
    }
}