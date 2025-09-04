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
    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    fun registerPermissions(mainActivity: MainActivity) {
        multiplePermissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){}

        // Background location launcher
        permissionLauncher = mainActivity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                Toast.makeText(mainActivity, ConstantStrings.BACKGROUND_LOCATION_PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestLocationPermissions() {
        multiplePermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    fun promptBackgroundLocation(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !isBackgroundLocationGranted(activity)) {
            AlertDialog.Builder(activity)
                .setTitle(ConstantStrings.BACKGROUND_LOCATION_PERMISSION)
                .setMessage(ConstantStrings.BACKGROUND_LOCATION_PERMISSION_MESSAGE)
                .setPositiveButton(ConstantStrings.ALLOW) { _, _ ->
                    // Request permission
                    permissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                }
                .setNegativeButton(ConstantStrings.NO) { dialog, _ ->
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
        AlertDialog.Builder(activity)
            .setTitle(ConstantStrings.PERMISSION_REQUIRED)
            .setMessage(ConstantStrings.PERMISSION_REQUIRED_LOCATION_MESSAGE)
            .setPositiveButton(ConstantStrings.OPEN_SETTINGS) { _, _ ->
                openAppPermissionSettings(activity)
            }
            .setNegativeButton(ConstantStrings.CANCEL) { _, _ ->
            }
            .show()
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

    fun openAppPermissionSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}