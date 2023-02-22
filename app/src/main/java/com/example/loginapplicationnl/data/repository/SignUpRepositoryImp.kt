package com.example.loginapplicationnl.data.repository

import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.model.SignUpRequest
import com.example.loginapplicationnl.data.service.ApiServices

class SignUpRepositoryImp(private val apiServices: ApiServices) : SignUpRepository {
    override suspend fun postSignUp(signUpRequest: SignUpRequest): LoginResponses {
        return apiServices.postSignUp(signUpRequest)
    }
}