package com.gandor.mobile_locator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import com.gandor.mobile_locator.layers.ui.viewmodels.PanelHostViewModel

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT

        PermissionManager.registerPermissions(this)
        PermissionManager.requestLocationPermissions()

        startLocationService(this)

        setContent {
            Mobile_locatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackHandler {
                        PanelHostViewModel.switchBackToRecentPanel()
                    }

                    MainComposable()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("GEO TEST", "onResume")
    }

    fun startLocationService(context: Context) {
        if (!LocationService.isRunning && PermissionManager.isFineOrCourseGrainedPermissionGranted(context)) {
            val serviceIntent = Intent(this, LocationService::class.java)
            startForegroundService(serviceIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        println("GEO TEST | !PermissionManager.isBackgroundLocationGranted(this): ${!PermissionManager.isBackgroundLocationGranted(this)}")
        if (!PermissionManager.isBackgroundLocationGranted(this)) {
            stopService(Intent(this, LocationService::class.java))
            LocationManager.stopLocationUpdates(this)
        }
    }
}