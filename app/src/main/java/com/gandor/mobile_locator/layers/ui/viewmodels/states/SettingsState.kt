package com.gandor.mobile_locator.layers.ui.viewmodels.states

import com.gandor.mobile_locator.layers.data.event.Event

data class SettingsState(
    val isShowCoordinatesClicked: Boolean = false,
    var isBackgroundLocationTurnedOn: Boolean = false
): Event()
