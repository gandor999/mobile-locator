package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gandor.mobile_locator.launchWithHandler
import com.gandor.mobile_locator.layers.data.constants.exceptions.ComposableException
import com.gandor.mobile_locator.layers.ui.composables.Page
import com.gandor.mobile_locator.layers.ui.viewmodels.interfaces.ErrorThrower
import com.gandor.mobile_locator.layers.ui.viewmodels.states.BaseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel(): ViewModel(), ErrorThrower {
    private val _baseState = MutableStateFlow(BaseState())
    val baseState = _baseState.asStateFlow()
    private var loadingStartTime: Long = 0L
    private lateinit var navHostController: NavHostController

    fun setNavHostController(navHostController: NavHostController) {
        if (!::navHostController.isInitialized) {
            this.navHostController = navHostController
        }
    }

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

    fun switchPages(page: Page) {
        launchWithHandler {
            if (::navHostController.isInitialized) {
                navHostController.navigate(page)
            }

//            PanelHostViewModel.switchPanel(goToFunction)
        }
    }

    fun switchBackToRecentPanel() {
        launchWithHandler {
            if (::navHostController.isInitialized) {
                navHostController.popBackStack()
            }
//            PanelHostViewModel.switchBackToRecentPanel()
        }
    }

    override fun throwError(composableException: ComposableException) {
//        ErrorDialogViewModel.showDialog(composableException.message.toString())
    }
}