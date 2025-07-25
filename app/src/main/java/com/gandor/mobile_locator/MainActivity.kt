package com.gandor.mobile_locator

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gandor.mobile_locator.managers.LocationManager
import com.gandor.mobile_locator.ui.theme.Mobile_locatorTheme
import com.gandor.mobile_locator.managers.PermissionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionManager.registerPermissions(this)
        PermissionManager.requestLocationPermission()

        setContent {
            Mobile_locatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

@Composable
fun MainComposable(mainActivity: MainActivity) {
    val scope = rememberCoroutineScope()
    val latitude = remember { mutableStateOf(0.00) }
    val longitude = remember { mutableStateOf(0.00) }
    val loading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(15.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            if (loading.value) {
                CircularProgressIndicator()
            } else {
                Text(text = "Latitude: ${latitude.value}")
                Text(text = "Longitude: ${longitude.value}")
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    scope.launch {
                        loading.value = true
                        val location = LocationManager(mainActivity).getCurrentLocation()
                        delay(1000) // give the user some time for their eyes to breath

                        if (location != null) {
                            latitude.value = location.latitude
                            longitude.value = location.longitude
                        }

                        loading.value = false
                    }
                }) {
                Text(text = "Show Coordinates")
            }

            Button(
                onClick = {
                    latitude.value = 0.00
                    longitude.value = 0.00
                }) {
                Text(text = "Clear")
            }
        }
    }
}