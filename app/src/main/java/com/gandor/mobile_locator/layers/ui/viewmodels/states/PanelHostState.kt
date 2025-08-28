package com.gandor.mobile_locator.layers.ui.viewmodels.states

import com.gandor.mobile_locator.layers.data.constants.PanelEnum

data class PanelHostState(
    val currentPanel: PanelEnum = PanelEnum.COORDINATES_PANEL,
)
