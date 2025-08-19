package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import androidx.compose.runtime.Composable
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@Composable
fun CoordinatesPanel(
    coordinatesViewModel: CoordinatesViewModel
) {
    MainCoordinatesPanel(coordinatesViewModel)
    ActionButtonsPanel(coordinatesViewModel)
}