package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.data.constants.exceptions.ComposableException
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.ErrorThrower
import com.gandor.mobile_locator.layers.ui.viewmodels.states.BaseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel: ViewModel(), ErrorThrower {
    private val _baseState = MutableStateFlow(BaseState())
    val baseState = _baseState.asStateFlow()
    private var loadingStartTime: Long = 0L

    open suspend fun setIsLoading(isLoading: Boolean, minDuration: Long = 2000) {
        if (isLoading) {
            // mark the start
            loadingStartTime = System.currentTimeMillis()
            _baseState.value = _baseState.value.copy(isLoading = true)
        } else {
            // ensure minimum duration
            val elapsed = System.currentTimeMillis() - loadingStartTime
            val remaining = minDuration - elapsed
            if (remaining > 0) {
                kotlinx.coroutines.delay(remaining)
            }
            _baseState.value = _baseState.value.copy(isLoading = false)
            loadingStartTime = 0L
        }
    }

    override fun throwError(composableException: ComposableException) {
//        ErrorDialogViewModel.showDialog(composableException.message.toString())
    }
}