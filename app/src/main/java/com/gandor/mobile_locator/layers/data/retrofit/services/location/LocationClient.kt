package com.gandor.mobile_locator.layers.data.retrofit.services.location

import com.gandor.mobile_locator.layers.data.constants.RemoteHosts
import com.gandor.mobile_locator.layers.data.exceptions.ServiceExceptionResponseMulti
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.data.retrofit.services.models.User
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocationClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(RemoteHosts.MOBILE_LOCATOR_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val locationService = retrofit.create(LocationService::class.java)

    suspend fun registerNewUser(newUser: User): ApiResult<Unit> {
        return locationService.registerNewUser(newUser).toApiResult()
    }
}

fun <T> Response<T>.toApiResult(): ApiResult<T> {
    if (isSuccessful) {
        return ApiResult.Success(body())
    }

    val errorBody = errorBody()?.string()

    try {
        val serviceExceptionResponseMulti = Gson().fromJson(errorBody, ServiceExceptionResponseMulti::class.java) as ServiceExceptionResponseMulti
        return ApiResult.Fail((serviceExceptionResponseMulti))
    } catch (e: Throwable) {
        e.printStackTrace()
        return ApiResult.NetworkFail(e)
    }

//    // TODO: do something here for the exception that has been returned
//    println("GEO TEST | locationException: $serviceExceptionResponseMulti")
//
//    ErrorDialogViewModel.showDialog((serviceExceptionResponseMulti as ServiceExceptionResponseMulti).status.toString() ?: "")
}