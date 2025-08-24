package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.ui.viewmodels.states.ErrorDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ErrorDialogViewModel: ViewModel() {
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()

    fun showDialog(errorMessages: List<String>) {
        _errorDialogState.value =
            _errorDialogState.value.copy(openErrorDialog = true, errorMessages = errorMessages)
    }

    fun reset() {
        _errorDialogState.value = ErrorDialogState()
    }
}