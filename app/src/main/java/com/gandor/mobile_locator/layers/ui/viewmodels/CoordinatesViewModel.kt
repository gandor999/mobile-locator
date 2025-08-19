package com.gandor.mobile_locator.layers.ui.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.ui.MarkerConstants
import com.gandor.mobile_locator.layers.ui.viewmodels.states.MainCoordinatePanelState
import com.gandor.mobile_locator.layers.ui.viewmodels.states.MapState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class CoordinatesViewModel : BaseViewModel(), MapListener {
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

    private fun setIsLoading(isLoading: Boolean) {
        _mainCoordinatePanelState.value =
            _mainCoordinatePanelState.value.copy(isLoading = isLoading)
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

    private fun setIsShowCoordinatesClicked(clicked: Boolean) {
        _mainCoordinatePanelState.value =
            _mainCoordinatePanelState.value.copy(isShowCoordinatesClicked = clicked)
    }

    @SuppressLint("MissingPermission")
    fun showCoordinates(
        activity: Activity
    ) {
        viewModelScope.launch {
            setIsShowCoordinatesClicked(true)
            setIsLoading(true)
            val location = LocationManager.getCurrentLocation(activity)

            delay(1000) // give the user some time for their eyes to breath

            if (location != null) {
                setLatitude(location.latitude)
                setLongitude(location.longitude)
            }

            setIsLoading(false)

            LocationManager.startLocationUpdates(activity, 1000L) // minimum request interval is 1 second
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
            title = MarkerConstants.YOU_ARE_HERE
            mapView.overlays?.add(this)
        }

        mapView.addMapListener(this)
    }
}