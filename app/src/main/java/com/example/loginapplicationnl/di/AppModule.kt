package com.example.loginapplicationnl.di

import com.example.loginapplicationnl.data.repository.LoginRepository
import com.example.loginapplicationnl.data.repository.LoginRepositoryImp
import com.example.loginapplicationnl.data.repository.SignUpRepository
import com.example.loginapplicationnl.data.repository.SignUpRepositoryImp
import com.example.loginapplicationnl.ui.login.LoginViewModel
import com.example.loginapplicationnl.ui.signUp.SignUpViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        SignUpViewModel(get())
    }
}

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImp(get()) }
    single<SignUpRepository> { SignUpRepositoryImp(get()) }
}

fun provideGson(): Gson? {
    val gsonBuilder = GsonBuilder()
    return gsonBuilder.create()
}