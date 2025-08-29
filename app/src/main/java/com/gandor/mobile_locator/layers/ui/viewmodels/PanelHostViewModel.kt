package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.data.constants.PanelEnum
import com.gandor.mobile_locator.layers.ui.viewmodels.states.PanelHostState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object PanelHostViewModel: ViewModel() {
    private val _panelHostState = MutableStateFlow(PanelHostState())
    val panelHostState = _panelHostState.asStateFlow()

    private const val MAX_BACK_STACK_DEPTH = 3 // only increase this when necessary

    fun switchPanel(panel: PanelEnum) {
        if (_panelHostState.value.panelHistoryStack.size == MAX_BACK_STACK_DEPTH) {
            _panelHostState.value.panelHistoryStack.removeAt(0)
        }

        _panelHostState.value.panelHistoryStack.add(panel)

        _panelHostState.value = _panelHostState.value.copy(
            currentPanel = panel
        )
    }

    fun switchBackToRecentPanel() {
        println("GEO TEST | _panelHostState.value.panelHistoryStack: " + _panelHostState.value.panelHistoryStack)
        if (_panelHostState.value.panelHistoryStack.size > 1) {
            val recentPanel = _panelHostState.value.panelHistoryStack[_panelHostState.value.panelHistoryStack.size - 2]
            _panelHostState.value.panelHistoryStack.removeAt(_panelHostState.value.panelHistoryStack.size - 1)

            _panelHostState.value = _panelHostState.value.copy(
                currentPanel = recentPanel
            )
        }
    }
}