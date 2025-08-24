package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.data.retrofit.services.location.LocationClient
import com.gandor.mobile_locator.layers.data.retrofit.services.models.User
import com.gandor.mobile_locator.layers.ui.viewmodels.states.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            val result = LocationClient.registerNewUser(
                User(
                    username = _registerState.value.username,
                    password = _registerState.value.password,
                    email = _registerState.value.email,
                )
            )

            when(result) {
                is ApiResult.Success -> {
                    // Do nothing
                }

                is ApiResult.Fail -> {
                    ErrorDialogViewModel.showDialog(result.failData.errors.map { it.message })
                }

                is ApiResult.NetworkFail -> {
                    ErrorDialogViewModel.showDialog(listOf(result.exception.message ?: "ApiResult.NetworkFail has no error message please raise issue with developer"))
                }
            }
        }
    }
}