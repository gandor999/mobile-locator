package com.gandor.mobile_locator.layers.ui.viewmodels

import com.gandor.mobile_locator.layers.ui.viewmodels.states.AccountResetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountResetViewModel() : BaseViewModel() {
    private val _accountResetState = MutableStateFlow(AccountResetState())
    private val accountResetState = _accountResetState.asStateFlow()

    fun setEmail(value: String) {
        _accountResetState.value = _accountResetState.value.copy(email = value)
    }

    fun setNewPassword(value: String) {
        _accountResetState.value = _accountResetState.value.copy(newPassword = value)
    }

    fun setOtp(value: String) {
        _accountResetState.value = _accountResetState.value.copy(otp = value)
    }
}