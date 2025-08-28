package com.gandor.mobile_locator.layers.data.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LocationService: Service() {
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location_channel",  // must match builder channelId
                "Location Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Location Tracking")
            .setContentText("Tracking your location in the background")
//            .setSmallIcon(R.drawable.ic_location)
            .setOngoing(true)
            .build()

        // This makes the service foreground (persistent)
        startForeground(1, notification)

        LocationManager.setFusedLocationClient(applicationContext)

        isRunning = true
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        LocationManager.startLocationUpdates(applicationContext, 1000L)
        val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        serviceScope.launch {
            LocationManager.locationFlow.collect { location ->
                location?.let {
                    Log.d("GEO TEST", "onStartCommand | Location update received: ${it.latitude}, ${it.longitude}")
                }
            }
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?) = null

    companion object {
        var isRunning = false
    }
}