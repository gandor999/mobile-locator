package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.PanelEnum
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun ActionButtonsPanel(
    coordinatesViewModel: CoordinatesViewModel,
    settingsViewModel: SettingsViewModel
) {
    val activity = LocalContext.current as? Activity
    val mainCoordinatePanelState = coordinatesViewModel.mainCoordinatePanelState.collectAsState().value

    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Button(
                onClick = {
                    coordinatesViewModel.switchPanels(PanelEnum.SETTINGS_PANEL)
                }) {
                Text(text = ConstantStrings.OPEN_SETTINGS)
            }

            Button(
                onClick = {
                    if (activity != null) {
                        coordinatesViewModel.openCoordinatesWithGoogle(activity)
                    }
                }) {
                Text(text = ConstantStrings.CoordinatesConstants.OPEN_IN_GOOGLE_MAPS)
            }
        }
    }
}