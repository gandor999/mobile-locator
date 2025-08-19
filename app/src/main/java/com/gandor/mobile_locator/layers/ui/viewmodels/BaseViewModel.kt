package com.gandor.mobile_locator.layers.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.gandor.mobile_locator.layers.data.constants.exceptions.ComposableException

open class BaseViewModel: ViewModel(), ErrorThrower {
    override fun throwError(composableException: ComposableException) {
        ErrorDialogViewModel.showDialog(composableException.message.toString())
    }
}