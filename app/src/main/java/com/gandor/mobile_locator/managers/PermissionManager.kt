package com.gandor.mobile_locator.managers

import android.Manifest
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
import kotlinx.coroutines.CompletableDeferred

object PermissionManager {
    private lateinit var mainActivity: MainActivity
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    var alreadyShowedPromptRequire = false

    fun registerPermissions(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
        locationPermissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                        permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (!granted) {
                Toast.makeText(mainActivity, "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun isFineLocationGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            mainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isCoarseLocationGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            mainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isNotAllowedToAskAgain(): Boolean {
        val isGranted = isFineLocationGranted() && isCoarseLocationGranted()
        val canAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(
            mainActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            mainActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return isGranted && canAskAgain
    }

    fun promptRequiredPermissions() {
        if (!alreadyShowedPromptRequire) {
            AlertDialog.Builder(mainActivity)
                .setTitle("Permission required")
                .setMessage("Location permission was permanently denied. Please enable it manually in app settings. Enable also 'precise' location.")
                .setPositiveButton("Open Settings") { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", mainActivity.packageName, null)
                    intent.data = uri
                    mainActivity.startActivity(intent)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    mainActivity.finishAffinity() // 👈 closes the app
                }
                .show()

            alreadyShowedPromptRequire = true

            return
        }
    }

    fun requestLocationPermission() {
        if (!isCoarseLocationGranted() && !isFineLocationGranted()) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}