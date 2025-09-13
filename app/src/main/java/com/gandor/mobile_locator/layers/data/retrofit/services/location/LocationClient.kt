package com.gandor.mobile_locator.layers.data.retrofit.services.location

import com.gandor.mobile_locator.layers.data.constants.RemoteHosts
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.data.retrofit.services.models.User
import com.gandor.mobile_locator.layers.data.retrofit.services.toApiResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocationClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(RemoteHosts.MOBILE_LOCATOR_HOST + RemoteHosts.LOCATION_ENTRYPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val locationService = retrofit.create(LocationService::class.java)

    suspend fun registerNewUser(newUser: User): ApiResult<Unit> {
        return locationService.registerNewUser(newUser).toApiResult()
    }
}

