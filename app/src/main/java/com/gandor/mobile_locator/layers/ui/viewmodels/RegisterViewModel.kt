package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.data.constants.PanelEnum
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
            setIsLoading(true)

            val result = LocationClient.registerNewUser(
                User(
                    username = _registerState.value.username,
                    password = _registerState.value.password,
                    email = _registerState.value.email,
                    androidId = _registerState.value.androidId
                )
            )

            setIsLoading(false)

            when(result) {
                is ApiResult.Success -> {
                    DialogViewModel.showSuccessDialog(listOf(ConstantStrings.RegistrationConstants.REGISTER_SUCCESS))
                    // TODO: move to login page

                    switchPanels(PanelEnum.COORDINATES_PANEL)
                }

                is ApiResult.Fail -> {
                    DialogViewModel.showErrorDialog(result.failData.errors.map { it.message })
                }

                is ApiResult.NetworkFail -> {
                    DialogViewModel.showErrorDialog(listOf(result.exception.message ?: ConstantStrings.NETWORK_FAIL_NO_MESSAGE))
                }
            }
        }
    }
}