package com.gandor.mobile_locator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gandor.mobile_locator.layers.data.managers.InitializeManager
import com.gandor.mobile_locator.layers.data.managers.PermissionManager
import com.gandor.mobile_locator.layers.ui.composables.MainComposable
import com.gandor.mobile_locator.layers.ui.theme.Mobile_locatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionManager.registerPermissions(this)
        PermissionManager.requestLocationPermission(this)

        InitializeManager().apply {
            initializeOpenStreetMapConfigs(this@MainActivity)
        }

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
}