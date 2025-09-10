package com.gandor.mobile_locator.layers.ui.viewmodels.states

import com.gandor.mobile_locator.layers.ui.PanelEnum

data class PanelState(
    val currentPanel: PanelEnum = PanelEnum.COORDINATES_PANEL,
    val panelHistoryStack: MutableList<PanelEnum> = mutableListOf(PanelEnum.COORDINATES_PANEL)
)
