package com.gandor.mobile_locator.layers.data.config

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.data.managers.PermissionManager
import com.gandor.mobile_locator.layers.data.service.LocationService

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("SourceLockedOrientationActivity")
class MainActivityConfigurator(private val mainActivity: MainActivity): Configurator {

    override fun initializeConfiguration() {
        mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT

        PermissionManager.registerPermissions(mainActivity)
        PermissionManager.requestLocationPermissions()

        startLocationService(mainActivity)
    }

    fun startLocationService(mainActivity: MainActivity) {
        if (!LocationService.isRunning && PermissionManager.isFineOrCourseGrainedPermissionGranted(mainActivity)) {
            val serviceIntent = Intent(mainActivity, LocationService::class.java)
            mainActivity.startForegroundService(serviceIntent)
        }
    }

    override fun handleClosing() {
        if (!PermissionManager.isBackgroundLocationGranted(mainActivity)) {
            mainActivity.stopService(Intent(mainActivity, LocationService::class.java))
            LocationManager.stopLocationUpdates(mainActivity)
        }
    }
}