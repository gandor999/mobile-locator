package com.gandor.mobile_locator.layers.ui.viewmodels.states

data class LoginState (
    val usernameOrEmail: String = "",
    val password: String = "",
    val androidId: String = "",
)