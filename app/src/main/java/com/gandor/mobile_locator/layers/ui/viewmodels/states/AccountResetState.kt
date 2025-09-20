package com.gandor.mobile_locator.layers.ui.viewmodels.states

data class AccountResetState(
    val email: String = "",
    val newPassword: String = "",
    val otp: String = ""
)
