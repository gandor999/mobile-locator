package com.gandor.mobile_locator.layers.data.retrofit.services

import com.gandor.mobile_locator.layers.data.constants.exceptions.ServiceExceptionResponseMulti
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.toApiResult(): ApiResult<T> {
    if (isSuccessful) {
        return ApiResult.Success(body())
    }

    try {
        val serviceExceptionResponseMulti = Gson().fromJson(
            errorBody()?.string(),
            ServiceExceptionResponseMulti::class.java
        ) as ServiceExceptionResponseMulti
        return ApiResult.Fail((serviceExceptionResponseMulti))
    } catch (e: Throwable) {
        e.printStackTrace()
        return ApiResult.NetworkFail(e)
    }
}