package com.gandor.mobile_locator.layers.ui.viewmodels

import com.gandor.mobile_locator.layers.ui.composables.Page
import com.gandor.mobile_locator.layers.ui.viewmodels.states.NavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel() : BaseViewModel() {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    fun setStartDestination(startDestination: Page) {
        _navigationState.value =
            _navigationState.value.copy(startDestination = startDestination)
    }
}