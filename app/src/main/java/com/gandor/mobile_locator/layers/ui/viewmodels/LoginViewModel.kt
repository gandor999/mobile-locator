package com.gandor.mobile_locator.layers.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.gandor.mobile_locator.launchWithHandler
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.data.retrofit.services.login.LoginClient
import com.gandor.mobile_locator.layers.ui.composables.CoordinatesPage
import com.gandor.mobile_locator.layers.ui.composables.LoginPage
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

    private fun setAndroidId(androidId: String) {
        _loginState.value =
            _loginState.value.copy(androidId = androidId)
    }

    @SuppressLint("HardwareIds")
    private fun generateAndroidId(context: Context) {
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        setAndroidId(androidId)
    }

    fun loginConventionally(context: Context) {
        launchWithHandler {
            setIsLoading(true)

            generateAndroidId(context)
            val result = LoginClient.loginUser(_loginState.value)

            if (result !is ApiResult.Success) {
                DialogViewModel.showErrorDialog(result)
            } else {
                switchPages(CoordinatesPage)
            }

            setIsLoading(false)
        }
    }

    fun isUserLoggedInAlready(context: Context, mainViewModel: MainViewModel) {
        launchWithHandler {
            setIsLoading(true)

            generateAndroidId(context)

            val result = LoginClient.isUserLoggedIn(_loginState.value)
            val isLoggedIn = result is ApiResult.Success && result.data == true

            mainViewModel.setStartDestination(if (isLoggedIn) CoordinatesPage else LoginPage)

            setIsLoading(false)
        }
    }

    fun loginWithAndroidId() {
    }
}