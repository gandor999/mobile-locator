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

@RequiresApi(Build.VERSION_CODES.Q)
object PermissionManager: ViewModel() {
    private lateinit var multiplePermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var fineGrainedPermissionDialog: AlertDialog? = null

    fun registerPermissions(context: Context) {
        when(context) {
            is MainActivity -> {
                multiplePermissionLauncher = context.registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ){}

                // Background location launcher
                permissionLauncher = context.registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { granted ->
                    if (!granted) {
                        Toast.makeText(context, ConstantStrings.BACKGROUND_LOCATION_PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
                    }
                }
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

    fun promptBackgroundLocationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !isBackgroundLocationGranted(context)) {
            AlertDialog.Builder(context)
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

    fun isFineOrCourseGrainedPermissionGranted(context: Context): Boolean {
        return isCoarseLocationGranted(context) && isFineLocationGranted(context)
    }

    fun isNotAllowedToAskAgain(context: Context): Boolean {
        val isAllNeededRequiredPermissionsGranted = isFineOrCourseGrainedPermissionGranted(context)
        val canAskAgain = (context as? Activity)?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.INTERNET
            )
        }
        return isAllNeededRequiredPermissionsGranted && canAskAgain == true
    }

    fun promptRequiredPermissions(context: Context) {
        if (!isFineOrCourseGrainedPermissionGranted(context)) {
            if (fineGrainedPermissionDialog != null && fineGrainedPermissionDialog?.isShowing == true) {
                return
            }

            fineGrainedPermissionDialog = AlertDialog.Builder(context)
                .setTitle(ConstantStrings.FOREGROUND_LOCATION_PERMISSION)
                .setMessage(ConstantStrings.FOREGROUND_LOCATION_PERMISSION_MESSAGE)
                .setPositiveButton(ConstantStrings.ALLOW) { _, _ ->
                    openAppPermissionSettings(context)
                    fineGrainedPermissionDialog = null
                }
                .setNegativeButton(ConstantStrings.NO) { _, _ ->
                    fineGrainedPermissionDialog = null
                }
                .setOnDismissListener {
                    fineGrainedPermissionDialog = null
                }
                .show()
        }
    }

    fun isBackgroundLocationGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun openAppPermissionSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}