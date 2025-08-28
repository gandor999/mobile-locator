package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@Composable
fun CoordinatesPanel(
    coordinatesViewModel: CoordinatesViewModel = viewModel()
) {
    MainCoordinatesPanel(coordinatesViewModel)
    ActionButtonsPanel(coordinatesViewModel)
}