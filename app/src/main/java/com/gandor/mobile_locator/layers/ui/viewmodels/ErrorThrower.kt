package com.gandor.mobile_locator.layers.ui.viewmodels

import com.gandor.mobile_locator.layers.data.constants.exceptions.ComposableException

interface ErrorThrower {
    fun throwError(composableException: ComposableException)
}