package com.gandor.mobile_locator.layers.data.retrofit.services.location

import com.gandor.mobile_locator.layers.data.retrofit.services.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationService {
    @POST("registerUser")
    suspend fun registerNewUser(@Body newUser: User): Response<Unit>
}