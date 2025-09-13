package com.gandor.mobile_locator.layers.ui.viewmodels

import android.util.Log
import com.gandor.mobile_locator.layers.data.constants.ConstantNumbers
import com.gandor.mobile_locator.layers.data.constants.ConstantStrings
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.ui.composables.RegisterPage
import com.gandor.mobile_locator.layers.ui.viewmodels.states.ErrorDialogState
import com.gandor.mobile_locator.layers.ui.viewmodels.states.SuccessDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object DialogViewModel: BaseViewModel() {
    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    private val _successDialogState = MutableStateFlow(SuccessDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()
    val successDialogState = _successDialogState.asStateFlow()

    fun showErrorDialog(errorMessages: List<String>) {
        _errorDialogState.value =
            _errorDialogState.value.copy(openErrorDialog = true, errorMessages = errorMessages)
    }

    fun <T : Any> showErrorDialog(apiResult: ApiResult<T>) {
        when(apiResult) {
            is ApiResult.Fail -> {
                Log.e("GEO TEST", "ApiResult.Fail")

                showErrorDialog(apiResult.failData.errors.map {

                    when(it.exceptionCode) {
                        ConstantNumbers.ExceptionCodes.USER_DOES_NOT_EXIST -> {
                            switchPages(RegisterPage)
                        }
                    }

                    it.message
                })
            }

            is ApiResult.NetworkFail -> {
                Log.e("GEO TEST", "ApiResult.NetworkFail")
                showErrorDialog(listOf(apiResult.exception.message ?: ConstantStrings.NETWORK_FAIL_NO_MESSAGE))
            }

            is ApiResult.Success -> {

            }
        }
    }

    fun showSuccessDialog(successMessages: List<String>) {
        _successDialogState.value =
            _successDialogState.value.copy(openSuccessDialog = true, successMessages = successMessages)
    }

    fun resetErrorDialog() {
        _errorDialogState.value = ErrorDialogState()
    }

    fun resetSuccessDialog() {
        _successDialogState.value = SuccessDialogState()
    }
}