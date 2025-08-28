package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.states.ErrorDialogState
import com.gandor.mobile_locator.layers.ui.viewmodels.states.SuccessDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object DialogViewModel: ViewModel() {
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    private val _successDialogState = MutableStateFlow(SuccessDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()
    val successDialogState = _successDialogState.asStateFlow()

    fun showErrorDialog(errorMessages: List<String>) {
        _errorDialogState.value =
            _errorDialogState.value.copy(openErrorDialog = true, errorMessages = errorMessages)
    }

    fun showSuccessDialog(successMessages: List<String>) {
        _successDialogState.value =
            _successDialogState.value.copy(openSuccessDialog = true, successMessages = successMessages)
    }

    fun resetErrorDialog() {
        _errorDialogState.value = ErrorDialogState()
    }

    fun resetSuccessDialog() {
        _successDialogState.value = SuccessDialogState()
    }
}