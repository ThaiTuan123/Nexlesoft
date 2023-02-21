package com.example.loginapplicationnl.data.repository

import com.example.loginapplicationnl.data.model.LoginRequest
import com.example.loginapplicationnl.data.model.LoginResponses
import com.example.loginapplicationnl.data.service.ApiServices
import com.example.loginapplicationnl.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class LoginRepositoryImp(private val apiServices: ApiServices) : LoginRepository {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponses {
        return apiServices.postSignIn(loginRequest)
    }
}