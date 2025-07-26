package com.gandor.mobile_locator.layers.ui.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.app.LocaleManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.ui.MarkerConstants
import com.gandor.mobile_locator.layers.ui.states.LocationManagerState
import com.gandor.mobile_locator.layers.ui.viewmodels.MainViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@SuppressLint("ContextCastToActivity")
@Composable
fun OpenStreetMapView(
    mainViewModel: MainViewModel
) {
    val markerRef = remember { mutableStateOf<Marker?>(null) }

    val mapState by mainViewModel.mapState.collectAsState()
    val locationManagerState by LocationManager.locationManagerState.collectAsState()

    val configuration = LocalConfiguration.current
    val currentOrientation = configuration.orientation
    val context = LocalContext.current

    var lastOrientation by rememberSaveable { mutableIntStateOf(currentOrientation) }
    val isLocationUpdatesStarted by rememberSaveable { mutableStateOf(locationManagerState.isLocationUpdatesStarted) }

    LaunchedEffect(lastOrientation) {
        if (lastOrientation != currentOrientation && isLocationUpdatesStarted) {
            lastOrientation = currentOrientation
            if (context is Activity) {
                mainViewModel.showCoordinates(context)
            }
        }

        LocationManager.locationFlow.collect { location ->
            location?.let {
                mainViewModel.setCoordinates(it.latitude, it.longitude)
                Log.d("GEO TEST", "Location update received: ${it.latitude}, ${it.longitude}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds() // 👈 This restricts overflow!
    ) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    markerRef.value = Marker(this)
                    mainViewModel.handleMapViewPositioning(this, markerRef)
                }
            },
            update = {
                markerRef.value?.position = GeoPoint(mapState.latitude, mapState.longitude)
                it.invalidate()
            }
        )
    }
}