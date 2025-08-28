package com.gandor.mobile_locator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.data.managers.PermissionManager
import com.gandor.mobile_locator.layers.data.service.LocationService
import com.gandor.mobile_locator.layers.ui.composables.MainComposable
import com.gandor.mobile_locator.layers.ui.theme.Mobile_locatorTheme
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: revisit how unhandled exceptions should be handled
//        Thread.setDefaultUncaughtExceptionHandler(GlobalErrorManager)
        PermissionManager.registerPermissions(this)
        PermissionManager.requestLocationPermissions()
        startLocationService()
        CoordinatesViewModel.initializeOpenStreetMapConfigs(this)

        setContent {
            Mobile_locatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable()
                }
            }
        }
    }

    fun startLocationService() {
        if (!LocationService.isRunning) {
            val serviceIntent = Intent(this, LocationService::class.java)
            startForegroundService(serviceIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!PermissionManager.isBackgroundLocationGranted(this)) {
            stopService(Intent(this, LocationService::class.java))
            LocationManager.stopLocationUpdates()
        }
    }
}