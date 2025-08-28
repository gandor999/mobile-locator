package com.gandor.mobile_locator.layers.ui.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.ui.viewmodels.DialogViewModel

@Composable
fun SuccessDialog(
    dialogViewModel: DialogViewModel
) {
    val successDialogState = dialogViewModel.successDialogState.collectAsState()

    AlertDialog(
        onDismissRequest = {
            dialogViewModel.resetSuccessDialog()
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    dialogViewModel.resetSuccessDialog()
                }) {
                    Text(ConstantStrings.ButtonTextConstants.OK)
                }
            }
        },
        title = { Text(ConstantStrings.ButtonTextConstants.SUCCESS) },
        text = {
            Column {
                successDialogState.value.successMessages.forEach {
                    Text(it)
                }
            }
        }
    )
}