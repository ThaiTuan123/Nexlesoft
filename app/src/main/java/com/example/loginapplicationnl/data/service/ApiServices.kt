package com.example.loginapplicationnl.data.service

import com.example.loginapplicationnl.data.model.LoginRequest
import com.example.loginapplicationnl.data.model.LoginResponses
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("auth/signin")
    suspend fun postSignIn(@Body loginRequest: LoginRequest): LoginResponses

}