package com.gandor.mobile_locator.layers.data.retrofit.services.login

import com.gandor.mobile_locator.layers.data.constants.RemoteHosts
import com.gandor.mobile_locator.layers.data.retrofit.services.ApiResult
import com.gandor.mobile_locator.layers.data.retrofit.services.toApiResult
import com.gandor.mobile_locator.layers.ui.viewmodels.states.LoginState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(RemoteHosts.MOBILE_LOCATOR_HOST + RemoteHosts.LOGIN_ENTRYPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val loginService = retrofit.create(LoginService::class.java)

    suspend fun loginUser(user: LoginState): ApiResult<Unit> {
        return loginService.loginUser(user).toApiResult()
    }

    suspend fun logoutUser(user: LoginState): ApiResult<Unit> {
        return loginService.logoutUser(user).toApiResult()
    }

    suspend fun isUserLoggedIn(user: LoginState): ApiResult<Boolean> {
        return loginService.isUserLoggedIn(user.androidId).toApiResult()
    }
}