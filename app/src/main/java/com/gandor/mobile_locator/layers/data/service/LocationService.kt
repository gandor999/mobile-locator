package com.gandor.mobile_locator.layers.data.service

import android.Manifest
import android.app.Service
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.gandor.mobile_locator.layers.data.managers.LocationManager

class LocationService: Service() {
    override fun onCreate() {
        super.onCreate()
        LocationManager.setFusedLocationClient(applicationContext)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        LocationManager.startLocationUpdates(applicationContext, 5000L)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationManager.stopLocationUpdates()
    }

    override fun onBind(intent: Intent?) = null
}