package com.gandor.mobile_locator.layers.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.Listener
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.Notifier
import com.gandor.mobile_locator.layers.ui.viewmodels.states.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel: BaseViewModel(), Notifier {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    private val listeners: MutableList<Listener> = mutableListOf()

    override fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun notifyListeners(context: Context?) {
        context?.let {
            listeners.forEach {
                it.consumeEvent(_settingsState.value, context)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun setIsShowCoordinatesClicked(context: Context, clicked: Boolean) {
        _settingsState.value =
            _settingsState.value.copy(isShowCoordinatesClicked = clicked)

        notifyListeners(context)
    }

    fun setIsBackgroundLocationTurnedOn(context: Context, bool: Boolean) {
        _settingsState.value =
            _settingsState.value.copy(isBackgroundLocationTurnedOn = bool)

        notifyListeners(context)
    }
}