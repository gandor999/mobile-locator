package com.gandor.mobile_locator.layers.data.constants

import androidx.compose.runtime.Composable
import com.gandor.mobile_locator.layers.ui.composables.panels.coordinates.CoordinatesPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.register.RegisterPanel

enum class PanelEnum(val composablePanel: @Composable () -> Unit) {
    REGISTER_PANEL({ RegisterPanel() }),
    COORDINATES_PANEL({ CoordinatesPanel() }),
    ;

    companion object {
        fun getPanel(panel: PanelEnum): PanelEnum? {
            return PanelEnum.entries.find { it == panel }
        }
    }
}