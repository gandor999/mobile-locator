package com.gandor.mobile_locator.layers.ui.viewmodels.states

data class ErrorDialogState(
    val openErrorDialog: Boolean = false,
    val errorMessages: List<String> = listOf("")
)
