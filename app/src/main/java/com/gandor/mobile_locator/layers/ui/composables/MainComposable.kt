package com.gandor.mobile_locator.layers.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.ui.composables.panels.coordinates.CoordinatesPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.register.RegisterPanel
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel

@Composable
fun MainComposable(
    coordinatesViewModel: CoordinatesViewModel = viewModel()
) {
    // add verify token from email for password change on forget or username on forget
    // add forgot password page
    // add register page
    // add login page if 
    // add main menu
    Column(
        modifier = Modifier.padding(ConstantNumbers.MAIN_PADDING.dp),
    ) {
        RegisterPanel()
//        CoordinatesPanel(coordinatesViewModel)
    }
}