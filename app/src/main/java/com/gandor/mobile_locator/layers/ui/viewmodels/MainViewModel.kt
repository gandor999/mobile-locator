package com.gandor.mobile_locator.layers.ui.viewmodels

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.managers.LocationManager
import com.gandor.mobile_locator.layers.ui.states.MainCoordinatePanelState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _mainCoordinatePanelState = MutableStateFlow(MainCoordinatePanelState())

    val mainCoordinatePanelState = _mainCoordinatePanelState.asStateFlow()

    private fun setLatitude(latitude: Double) {
        _mainCoordinatePanelState.value =
            _mainCoordinatePanelState.value.copy(latitude = latitude)
    }

    private fun setLongitude(longitude: Double) {
        _mainCoordinatePanelState.value =
            _mainCoordinatePanelState.value.copy(longitude = longitude)
    }

    private fun setIsLoading(isLoading: Boolean) {
        _mainCoordinatePanelState.value =
            _mainCoordinatePanelState.value.copy(isLoading = isLoading)
    }

    fun showCoordinates(
        localActivity: Activity
    ) {
        viewModelScope.launch {
            setIsLoading(true)
            val location = LocationManager().getCurrentLocation(localActivity)
            delay(1000) // give the user some time for their eyes to breath

            if (location != null) {
                setLatitude(location.latitude)
                setLongitude(location.longitude)
            }

            setIsLoading(false)
        }
    }

    fun openCoordinatesWithGoogle(
        localActivity: Activity
    ) {
        val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${_mainCoordinatePanelState.value.latitude},${_mainCoordinatePanelState.value.longitude}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        localActivity.startActivity(intent)
    }
}