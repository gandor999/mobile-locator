package com.gandor.mobile_locator.layers.ui.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.data.event.Event
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.Listener
import com.gandor.mobile_locator.layers.ui.viewmodels.states.MainCoordinatePanelState
import com.gandor.mobile_locator.layers.ui.viewmodels.states.MapState
import com.gandor.mobile_locator.layers.ui.viewmodels.states.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class CoordinatesViewModel : BaseViewModel(), MapListener, Listener {
    private val _mainCoordinatePanelState = MutableStateFlow(MainCoordinatePanelState())
    private val _mapState = MutableStateFlow(MapState())
    val mainCoordinatePanelState = _mainCoordinatePanelState.asStateFlow()
    val mapState = _mapState.asStateFlow()

    private fun setLatitude(latitude: Double) {
        _mapState.value =
            _mapState.value.copy(latitude = latitude)
    }

    private fun setLongitude(longitude: Double) {
        _mapState.value =
            _mapState.value.copy(longitude = longitude)
    }

    private fun setZoomLevel(zoom: Double) {
        _mapState.value =
            _mapState.value.copy(zoomLevel = zoom)
    }

    fun setCoordinates(lat: Double, lon: Double) {
        _mapState.value =
            _mapState.value.copy(
                latitude = lat,
                longitude = lon
            )
    }

    @SuppressLint("MissingPermission")
    fun showCoordinates(
        activity: Activity
    ) {
        viewModelScope.launch {
            setIsLoading(true)
            val location = LocationManager.getCurrentLocation(activity)

            if (location != null) {
                setLatitude(location.latitude)
                setLongitude(location.longitude)
            }

            setIsLoading(false)

            LocationManager.startLocationUpdates(activity, 1000)
        }
    }

    fun disableCoordinates(context: Context) {
        viewModelScope.launch {
            setIsLoading(true)
            LocationManager.stopLocationUpdates(context)
            setIsLoading(false)
        }
    }

    fun openCoordinatesWithGoogle(
        activity: Activity
    ) {
        val uri =
            (
                ConstantStrings.GOOGLE_OPEN_LOCATION_URI +
                "${_mapState.value.latitude}," +
                "${_mapState.value.longitude}"
            )
                .toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity.startActivity(intent)
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        return false
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        val newZoom = event?.zoomLevel ?: return false
        setZoomLevel(newZoom)
        return true
    }

    fun handleMapViewPositioning(mapView: MapView, marker: MutableState<Marker?>) {
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        val position = GeoPoint(
            _mapState.value.latitude,
            _mapState.value.longitude
        )
        mapView.controller.setZoom(_mapState.value.zoomLevel)
        mapView.controller.setCenter(position)

        marker.value?.apply {
            this.position = position
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = ConstantStrings.CoordinatesConstants.YOU_ARE_HERE
            mapView.overlays?.add(this)
        }

        mapView.addMapListener(this)
    }

    override fun consumeEvent(event: Event, context: Context?) {
        context?.let {
            when(event) {
                is SettingsState -> {
                    if (event.isShowCoordinatesClicked) {
                        if (context is Activity) {
                            showCoordinates(context)
                        }
                    } else {
                        disableCoordinates(context)
                    }
                }
            }
        }
    }

    companion object {
        fun initializeOpenStreetMapConfigs(context: Context) {
            val sharedPrefs = context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)

            Configuration.getInstance().load(
                context,
                sharedPrefs
            )
        }
    }
}