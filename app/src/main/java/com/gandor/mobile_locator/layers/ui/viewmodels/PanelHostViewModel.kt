package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.data.constants.PanelEnum
import com.gandor.mobile_locator.layers.ui.viewmodels.states.PanelHostState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object PanelHostViewModel: ViewModel() {
    private val _panelHostState = MutableStateFlow(PanelHostState())
    val panelHostState = _panelHostState.asStateFlow()

    fun switchPanel(panel: PanelEnum) {
        _panelHostState.value = _panelHostState.value.copy(
            currentPanel = panel
        )
    }
}