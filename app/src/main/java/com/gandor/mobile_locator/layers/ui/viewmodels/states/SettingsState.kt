package com.gandor.mobile_locator.layers.ui.viewmodels.states

import com.gandor.mobile_locator.layers.data.event.Event

data class SettingsState(
    val isShowCoordinatesClicked: Boolean = false,
    var isBackgroundLocationTurnedOn: Boolean = false
): Event() {
    companion object {
        object SHARED_PREFERENCE_KEYS {
            const val IS_SHOW_COORDINATES_CLICKED = "is_show_coordinates_clicked"
            const val IS_BACKGROUND_LOCATION_TURNED_ON = "is_background_location_turned_on"
        }
    }
}
