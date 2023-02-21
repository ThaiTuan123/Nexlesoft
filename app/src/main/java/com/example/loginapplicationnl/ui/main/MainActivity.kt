package com.example.loginapplicationnl.ui.main

import android.view.LayoutInflater
import com.example.loginapplicationnl.base.BaseActivity
import com.example.loginapplicationnl.databinding.ActivityMainBinding
import com.example.loginapplicationnl.di.networkModule
import com.example.loginapplicationnl.di.repositoryModule
import com.example.loginapplicationnl.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun inflateViewBinding(inflater: LayoutInflater) =
        ActivityMainBinding.inflate(inflater)

    override fun initView() {
    }

    override fun initData() {
        // TODO
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }
}