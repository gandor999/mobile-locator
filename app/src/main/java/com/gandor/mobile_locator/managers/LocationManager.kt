package com.gandor.mobile_locator.managers

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.gandor.mobile_locator.MainActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationManager(private val mainActivity: MainActivity) {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        if (!PermissionManager.isAllNeededRequiredPermissionsGranted()) {
            if (!PermissionManager.isNotAllowedToAskAgain()) {
                PermissionManager.promptRequiredPermissions()
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