package com.gandor.mobile_locator.layers.data.managers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings

object PermissionManager: ViewModel() {
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var backgroundPermissionLauncher: ActivityResultLauncher<String>
    private var alreadyShowedPromptRequire = false

    fun registerPermissions(mainActivity: MainActivity) {
        locationPermissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (!granted) {
                Toast.makeText(mainActivity, ConstantStrings.PERMISSION_REQUIRED_DENIED, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Background location launcher
        backgroundPermissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                Toast.makeText(mainActivity, "Background location denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun promptBackgroundLocation(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !isBackgroundLocationGranted(activity)) {
            AlertDialog.Builder(activity)
                .setTitle("Background Location Permission")
                .setMessage("To track your location continuously, please allow background location access.")
                .setPositiveButton("Allow") { _, _ ->
                    // Request permission
                    backgroundPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun isFineLocationGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isCoarseLocationGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isAllNeededRequiredPermissionsGranted(context: Context): Boolean {
        return isCoarseLocationGranted(context) && isFineLocationGranted(context)
    }

    fun isNotAllowedToAskAgain(activity: Activity): Boolean {
        val isAllNeededRequiredPermissionsGranted = isAllNeededRequiredPermissionsGranted(activity)
        val canAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.INTERNET
        )
        return isAllNeededRequiredPermissionsGranted && canAskAgain
    }

    fun promptRequiredPermissions(activity: Activity) {
        if (!alreadyShowedPromptRequire) {
            AlertDialog.Builder(activity)
                .setTitle(ConstantStrings.PERMISSION_REQUIRED)
                .setMessage(ConstantStrings.PERMISSION_REQUIRED_LOCATION_MESSAGE)
                .setPositiveButton(ConstantStrings.OPEN_SETTINGS) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity.packageName, null)
                    intent.data = uri
                    activity.startActivity(intent)
                }
                .setNegativeButton(ConstantStrings.CANCEL) { _, _ ->
                    activity.finishAffinity() // 👈 closes the app
                }
                .show()

            alreadyShowedPromptRequire = true

            return
        }
    }

    fun requestLocationPermission(activity: Activity) {
        if (!isCoarseLocationGranted(activity) && !isFineLocationGranted(activity)) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        requestBackgroundLocationPermission(activity)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun isBackgroundLocationGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBackgroundLocationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!isBackgroundLocationGranted(activity)) {
                promptBackgroundLocation(activity)
            }
        }
    }
}