package com.gandor.mobile_locator.layers.data.retrofit.services.login

import com.gandor.mobile_locator.layers.ui.viewmodels.states.LoginState
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("login")
    suspend fun loginUser(@Body user: LoginState): Response<Unit>

    @POST("logout")
    suspend fun logoutUser(@Body user: LoginState): Response<Unit>

    @GET("isUserLoggedIn/androidId/{androidId}")
    suspend fun isUserLoggedIn(@Path("androidId") androidId: String): Response<Boolean>
}