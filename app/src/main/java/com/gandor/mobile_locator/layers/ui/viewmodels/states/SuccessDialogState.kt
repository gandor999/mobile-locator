package com.gandor.mobile_locator.layers.ui.viewmodels.states

data class SuccessDialogState(
    val openSuccessDialog: Boolean = false,
    val successMessages: List<String> = listOf("")
)
