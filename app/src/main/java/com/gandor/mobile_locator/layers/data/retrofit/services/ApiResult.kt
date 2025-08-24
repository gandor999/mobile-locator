package com.gandor.mobile_locator.layers.data.retrofit.services

import com.gandor.mobile_locator.layers.data.exceptions.ServiceExceptionResponseMulti

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Fail(val failData: ServiceExceptionResponseMulti) : ApiResult<Nothing>()
    data class NetworkFail(val exception: Throwable) : ApiResult<Nothing>()
}