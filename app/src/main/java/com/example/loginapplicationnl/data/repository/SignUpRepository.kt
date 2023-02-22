package com.example.loginapplicationnl.data.repository

import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.model.SignUpRequest

interface SignUpRepository {
    suspend fun postSignUp(signUpRequest: SignUpRequest) : LoginResponses
}