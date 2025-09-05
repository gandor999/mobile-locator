package com.gandor.mobile_locator.layers.data.constants

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.ui.composables.panels.coordinates.CoordinatesPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.register.RegisterPanel
import com.gandor.mobile_locator.layers.ui.composables.panels.settings.SettingsPanel
import com.gandor.mobile_locator.layers.ui.viewmodels.BaseViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

enum class PanelEnum(
    val composablePanel: @Composable (
        SettingsViewModel,
        BaseViewModel
    ) -> Unit
) {
    REGISTER_PANEL({ settingsViewModel, baseViewModel ->
        RegisterPanel(
            settingsViewModel = settingsViewModel,
            registerViewModel = baseViewModel as RegisterViewModel
        )
    }),
    COORDINATES_PANEL({ settingsViewModel, baseViewModel ->
        CoordinatesPanel(
            settingsViewModel = settingsViewModel,
            coordinatesViewModel = baseViewModel as CoordinatesViewModel
        )
    }),
    @RequiresApi(Build.VERSION_CODES.Q)
    SETTINGS_PANEL({ settingsViewModel, baseViewModel ->
        SettingsPanel(
            settingsViewModel = settingsViewModel,
        )
    }),
    ;

    companion object {
        private fun getPanel(panel: PanelEnum): PanelEnum? {
            return PanelEnum.entries.find { it == panel }
        }

        @Composable
        fun showPanel(panel: PanelEnum, availableBaseViewModels: List<BaseViewModel>) {
            // there has got to be a better way of doing this instead of using jetpack navigator
            val chosenPanel = getPanel(panel)
            val settingsViewModel = getAppropriateViewModel<SettingsViewModel>(availableBaseViewModels) as? SettingsViewModel

            settingsViewModel?.let {
                when(chosenPanel) {
                    REGISTER_PANEL -> {
                        getAppropriateViewModel<RegisterViewModel>(availableBaseViewModels)?.let {
                            chosenPanel.composablePanel.invoke(settingsViewModel, it as RegisterViewModel)
                        }
                    }
                    COORDINATES_PANEL -> {
                        getAppropriateViewModel<CoordinatesViewModel>(availableBaseViewModels)?.let {
                            chosenPanel.composablePanel.invoke(settingsViewModel, it as CoordinatesViewModel)
                        }
                    }
                    SETTINGS_PANEL -> {
                        chosenPanel.composablePanel.invoke(settingsViewModel, settingsViewModel)
                    }
                    else -> {}
                }
            }
        }

        private inline fun <reified T> getAppropriateViewModel(availableBaseViewModels: List<BaseViewModel>): ViewModel? {
            return availableBaseViewModels.find { it is T }
        }
    }
}