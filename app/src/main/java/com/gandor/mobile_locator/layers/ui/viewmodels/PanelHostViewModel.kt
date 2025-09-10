package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.ui.PanelEnum
import com.gandor.mobile_locator.layers.ui.viewmodels.states.PanelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object PanelHostViewModel: ViewModel() {
    private val _panelState = MutableStateFlow(PanelState())
    val panelHostState = _panelState.asStateFlow()

    private const val MAX_BACK_STACK_DEPTH = 3 // only increase this when necessary

    fun switchPanel(panel: PanelEnum) {
        if (_panelState.value.panelHistoryStack.size == MAX_BACK_STACK_DEPTH) {
            _panelState.value.panelHistoryStack.removeAt(0)
        }

        _panelState.value.panelHistoryStack.add(panel)

        _panelState.value = _panelState.value.copy(
            currentPanel = panel
        )
    }

    fun switchBackToRecentPanel() {
        println("GEO TEST | _panelHostState.value.panelHistoryStack: " + _panelState.value.panelHistoryStack)
        if (_panelState.value.panelHistoryStack.size > 1) {
            val recentPanel = _panelState.value.panelHistoryStack[_panelState.value.panelHistoryStack.size - 2]
            _panelState.value.panelHistoryStack.removeAt(_panelState.value.panelHistoryStack.size - 1)

            _panelState.value = _panelState.value.copy(
                currentPanel = recentPanel
            )
        }
    }
}