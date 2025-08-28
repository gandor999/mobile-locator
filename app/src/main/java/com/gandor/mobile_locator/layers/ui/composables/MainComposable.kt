package com.gandor.mobile_locator.layers.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.PanelEnum
import com.gandor.mobile_locator.layers.ui.composables.dialogs.ErrorDialog
import com.gandor.mobile_locator.layers.ui.composables.dialogs.SuccessDialog
import com.gandor.mobile_locator.layers.ui.viewmodels.CoordinatesViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.DialogViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.PanelHostViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.RegisterViewModel

@Composable
fun MainComposable(
    coordinatesViewModel: CoordinatesViewModel = viewModel(),
    registerViewModel: RegisterViewModel = viewModel(),
) {
    // add verify token from email for password change on forget or username on forget
    // add forgot password page
    // add register page
    // add login page if 
    // add main menu
    Column(
        modifier = Modifier.padding(ConstantNumbers.MAIN_PADDING.dp),
    ) {
        val errorDialogState = DialogViewModel.errorDialogState.collectAsState()
        val successDialogState = DialogViewModel.successDialogState.collectAsState()
        val panelHostState = PanelHostViewModel.panelHostState.collectAsState()

        PanelEnum.getPanel(panelHostState.value.currentPanel)?.composablePanel?.invoke()

        if (errorDialogState.value.openErrorDialog) {
            ErrorDialog(DialogViewModel)
        }

        if (successDialogState.value.openSuccessDialog) {
            SuccessDialog(DialogViewModel)
        }

//        RegisterPanel(registerViewModel)
////        CoordinatesPanel(coordinatesViewModel)
    }
}