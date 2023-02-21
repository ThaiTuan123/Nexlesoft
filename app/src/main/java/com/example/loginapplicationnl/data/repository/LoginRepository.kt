package com.example.loginapplicationnl.data.repository

import com.example.loginapplicationnl.data.model.LoginRequest
import com.example.loginapplicationnl.data.model.LoginResponses

interface LoginRepository {
    suspend fun postLogin(loginRequest: LoginRequest) : LoginResponses
}