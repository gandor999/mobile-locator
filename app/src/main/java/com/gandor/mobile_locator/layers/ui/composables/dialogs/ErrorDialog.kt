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
import com.gandor.mobile_locator.layers.ui.viewmodels.ErrorDialogViewModel

@Composable
fun ErrorDialog(
    errorDialogViewModel: ErrorDialogViewModel
) {
    val errorDialogState = errorDialogViewModel.errorDialogState.collectAsState()

    AlertDialog(
        onDismissRequest = {
            errorDialogViewModel.reset()
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    errorDialogViewModel.reset()
                }) {
                    Text("Ok")
                }
            }
        },
        title = { Text("Error") },
        text = {
            Column {
                Text("Error Message: ${errorDialogState.value.errorMessage}")
//                mutableStates.errorDialogConfig.value.throwable?.let {
//                    Text("Error Type: " + it::class.simpleName)
//                }
            }
        }
    )
}