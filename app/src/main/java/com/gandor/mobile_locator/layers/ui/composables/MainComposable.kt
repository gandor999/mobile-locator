package com.gandor.mobile_locator.layers.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.MainActivity
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.PanelEnum
import com.gandor.mobile_locator.layers.ui.composables.dialogs.ErrorDialog
import com.gandor.mobile_locator.layers.ui.composables.dialogs.SuccessDialog
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.DialogViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.PanelHostViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainComposable() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val registerViewModel: RegisterViewModel = viewModel()
    val coordinatesViewModel: CoordinatesViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    val errorDialogState = DialogViewModel.errorDialogState.collectAsState()
    val successDialogState = DialogViewModel.successDialogState.collectAsState()
    val panelHostState = PanelHostViewModel.panelHostState.collectAsState()

    settingsViewModel.registerListener(coordinatesViewModel)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                settingsViewModel.syncWithSharedPreference(context)
                settingsViewModel.syncPermissions(context)

                when(context) {
                    is MainActivity -> {
                        context.mainActivityConfigurator.startLocationService(context)
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier.padding(ConstantNumbers.MAIN_PADDING.dp),
    ) {
        if (errorDialogState.value.openErrorDialog) {
            ErrorDialog(DialogViewModel)
        }

        if (successDialogState.value.openSuccessDialog) {
            SuccessDialog(DialogViewModel)
        }

        PanelEnum.showPanel(
            panelHostState.value.currentPanel,
            listOf(
                registerViewModel,
                coordinatesViewModel,
                settingsViewModel
            )
        )
    }
}