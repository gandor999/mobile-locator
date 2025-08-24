package com.gandor.mobile_locator.layers.data.retrofit.services.location

import com.gandor.mobile_locator.layers.data.constants.RemoteHosts
import com.gandor.mobile_locator.layers.data.exceptions.ServiceExceptionResponseMulti
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

    suspend fun registerNewUser(newUser: User): Response<Unit> {
        return locationService.registerNewUser(newUser).handleErrors()
    }
}

fun <T> Response<T>.handleErrors(): Response<T> {
    if (isSuccessful) {
        return this
    }

    val errorBody = errorBody()?.string()
    val serviceExceptionResponseMulti = try {
        Gson().fromJson(errorBody, ServiceExceptionResponseMulti::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // TODO: do something here for the exception that has been returned
    println("GEO TEST | locationException: $serviceExceptionResponseMulti")

    return this
}