package com.gandor.mobile_locator.layers.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.MainViewModel

@Composable
fun MainComposable(
    mainViewModel: MainViewModel = viewModel()
) {
    Column(
        modifier = Modifier.padding(15.dp),
    ) {
        MainCoordinatesPanel(mainViewModel)
        ActionButtonsPanel(mainViewModel)
    }
}

