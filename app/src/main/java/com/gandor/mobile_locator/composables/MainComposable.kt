package com.gandor.mobile_locator.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.managers.LocationManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainComposable(mainActivity: MainActivity) {
    val scope = rememberCoroutineScope()
    val latitude = rememberSaveable  { mutableStateOf(0.00) }
    val longitude = rememberSaveable  { mutableStateOf(0.00) }
    val loading = rememberSaveable  { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
//                .border(2.dp, Color.Red)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (loading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center // 👈 centers both vertically and horizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                SelectionContainer {
                    Column {
                        val textStyle = MaterialTheme.typography.bodyLarge
                        val fontSize = textStyle.fontSize

                        val iconSizeDp = with(LocalDensity.current) { fontSize.toDp() }

                        Row(
                           horizontalArrangement = Arrangement.SpaceBetween,
                           verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Latitude: ${latitude.value}")
                            CopyCoordinatesLatitude(latitude.value.toString(), iconSizeDp)
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Longitude: ${longitude.value}")
                            CopyCoordinatesLongitude(longitude.value.toString(), iconSizeDp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OpenStreetMapView(
                    lat = latitude.value,
                    lon = longitude.value
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
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
                        val lat = latitude.value
                        val lon = longitude.value
                        val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lon")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        mainActivity.startActivity(intent)
                    }) {
                    Text(text = "Open In Google Maps")
                }
            }
        }
    }
}

@Composable
fun CopyCoordinatesLatitude(latitude: String, iconSize: Dp) {
    val clipboardManager = LocalClipboardManager.current

    IconButton(onClick = {
        clipboardManager.setText(AnnotatedString(latitude))
    },
        modifier = Modifier
            .size(iconSize)
    ) {
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = "Copy Latitude"
        )
    }
}

@Composable
fun CopyCoordinatesLongitude(longitude: String, iconSize: Dp) {
    val clipboardManager = LocalClipboardManager.current

    IconButton(onClick = {
        clipboardManager.setText(AnnotatedString(longitude))
    },
        modifier = Modifier
            .size(iconSize)
    ){
        Icon(
            imageVector = Icons.Filled.ContentCopy,
            contentDescription = "Copy Longitude"
        )
    }
}