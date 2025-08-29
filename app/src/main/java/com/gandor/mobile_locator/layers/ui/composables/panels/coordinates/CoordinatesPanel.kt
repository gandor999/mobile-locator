package com.gandor.mobile_locator.layers.ui.composables.panels.coordinates

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.layers.ui.composables.PopOutEntryAndExit
import com.gandor.mobile_locator.layers.ui.composables.SlideDownEntry
import com.gandor.mobile_locator.layers.ui.composables.SlideInEntry
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun CoordinatesPanel(
    settingsViewModel: SettingsViewModel,
    coordinatesViewModel: CoordinatesViewModel = viewModel()
) {
    val activity = LocalContext.current as? Activity
    val mainCoordinatePanelState = coordinatesViewModel.mainCoordinatePanelState.collectAsState().value
    val coordinatesBaseState = coordinatesViewModel.baseState.collectAsState().value
    val settingsState  = settingsViewModel.settingsState.collectAsState().value
    val baseSettingsState = settingsViewModel.baseState.collectAsState().value

    Box {
        PopOutEntryAndExit(!settingsState.isShowCoordinatesClicked && !coordinatesBaseState.isLoading) {
            Text("Not emitting location right now")
        }

        SlideDownEntry(settingsState.isShowCoordinatesClicked && coordinatesBaseState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f),
                contentAlignment = Alignment.Center // 👈 centers both vertically and horizontally
            ) {
                CircularProgressIndicator()
            }
        }

        PopOutEntryAndExit(settingsState.isShowCoordinatesClicked && !coordinatesBaseState.isLoading) {
            MainCoordinatesPanel(coordinatesViewModel)
        }

    }

    SlideInEntry {
        ActionButtonsPanel(coordinatesViewModel, settingsViewModel)
    }
}