package com.gandor.mobile_locator.layers.ui.viewmodels

import com.gandor.mobile_locator.layers.ui.viewmodels.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel() : BaseViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun setUsernameOrEmail(usernameOrEmail: String) {
        _loginState.value =
            _loginState.value.copy(usernameOrEmail = usernameOrEmail)
    }

    fun setPassword(password: String) {
        _loginState.value =
            _loginState.value.copy(password = password)
    }

    fun setAndroidId(androidId: String) {
        _loginState.value =
            _loginState.value.copy(androidId = androidId)
    }

    fun loginConventionally() {

    }

    fun isUserLoggedInAlready(): Boolean {
        return false
    }

    fun loginWithAndroidId() {
    }
}