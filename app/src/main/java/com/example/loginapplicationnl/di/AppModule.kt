package com.example.loginapplicationnl.di

import com.example.loginapplicationnl.data.repository.LoginRepository
import com.example.loginapplicationnl.data.repository.LoginRepositoryImp
import com.example.loginapplicationnl.data.service.ApiServices
import com.example.loginapplicationnl.ui.login.LoginViewModel
import com.example.loginapplicationnl.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val viewModelModule = module {
    viewModel {
        LoginViewModel(get())
    }
}

val repositoryModule = module {
//    single { LoginRepositoryImp(get()) }
    single<LoginRepository> { LoginRepositoryImp(get()) }
}





fun provideGson(): Gson? {
    val gsonBuilder = GsonBuilder()
    return gsonBuilder.create()
}