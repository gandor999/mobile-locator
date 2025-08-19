package com.gandor.mobile_locator.layers.data.managers

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.data.constants.ConstantMessages

object PermissionManager {
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private var alreadyShowedPromptRequire = false

    fun registerPermissions(mainActivity: MainActivity) {
        locationPermissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (!granted) {
                Toast.makeText(mainActivity, ConstantMessages.PERMISSION_REQUIRED_DENIED, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isFineLocationGranted(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isCoarseLocationGranted(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isAllNeededRequiredPermissionsGranted(activity: Activity): Boolean {
        return isCoarseLocationGranted(activity) && isFineLocationGranted(activity)
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
                .setTitle(ConstantMessages.PERMISSION_REQUIRED)
                .setMessage(ConstantMessages.PERMISSION_REQUIRED_LOCATION_MESSAGE)
                .setPositiveButton(ConstantMessages.OPEN_SETTINGS) { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity.packageName, null)
                    intent.data = uri
                    activity.startActivity(intent)
                }
                .setNegativeButton(ConstantMessages.CANCEL) { _, _ ->
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
    }
}