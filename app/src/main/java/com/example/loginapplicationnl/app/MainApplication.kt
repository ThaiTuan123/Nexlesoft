package com.example.loginapplicationnl.app

import android.app.Application
import com.example.loginapplicationnl.di.appComponent
import com.example.loginapplicationnl.di.networkModule
import com.example.loginapplicationnl.di.repositoryModule
import com.example.loginapplicationnl.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initiateKoin()

    }
    private fun initiateKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}