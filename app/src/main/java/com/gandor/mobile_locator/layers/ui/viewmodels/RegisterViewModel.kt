package com.gandor.mobile_locator.layers.ui.viewmodels

import com.gandor.mobile_locator.layers.data.constants.exceptions.InvalidEmail
import com.gandor.mobile_locator.layers.ui.viewmodels.states.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel: BaseViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    fun setUsername(username: String) {
        _registerState.value =
            _registerState.value.copy(username = username)
    }

    fun setPassword(password: String) {
        _registerState.value =
            _registerState.value.copy(password = password)
    }

    fun setEmail(email: String) {
        _registerState.value =
            _registerState.value.copy(email = email)
    }

    fun setAndroidId(androidId: String) {
        _registerState.value =
            _registerState.value.copy(androidId = androidId)
    }

    fun submit() {

//        throwError(InvalidEmail())
    }
}