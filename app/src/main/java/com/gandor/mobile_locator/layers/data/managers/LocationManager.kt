package com.gandor.mobile_locator.layers.data.managers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.gandor.mobile_locator.layers.data.service.LocationService
import com.gandor.mobile_locator.layers.ui.viewmodels.states.LocationManagerState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object LocationManager {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    val locationFlow = MutableStateFlow<Location?>(null)

    private val _locationManagerState = MutableStateFlow(LocationManagerState())
    val locationManagerState = _locationManagerState.asStateFlow()

    fun setIsLocationUpdatesStarted(started: Boolean) {
        _locationManagerState.value = _locationManagerState.value.copy(
            isLocationUpdatesStarted = started
        )
    }

    fun initialize(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                locationFlow.value = result.lastLocation
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun startLocationUpdates(context: Context, updateIntervalMillis: Long) {
        if (!::fusedLocationClient.isInitialized) {
            initialize(context)
        }

        if (!PermissionManager.isFineOrCourseGrainedPermissionGranted(context)) {
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            updateIntervalMillis
        ).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        setIsLocationUpdatesStarted(true)
    }

    fun stopLocationUpdates(context: Context) {
        if (_locationManagerState.value.isLocationUpdatesStarted) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            setIsLocationUpdatesStarted(false)

            if (LocationService.isRunning) {
                val intent = Intent(context, LocationService::class.java)
                context.stopService(intent)
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(activity: Activity): Location? {
        if (!::fusedLocationClient.isInitialized) {
            initialize(activity)
        }

        if (!PermissionManager.isFineOrCourseGrainedPermissionGranted(activity)) {
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