package com.example.authsystem.app

import android.app.Application
import com.example.authsystem.di.loadRepositories
import com.example.authsystem.di.loadUseCase
import com.example.authsystem.di.loadViewModel
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@MyApp)
            modules(loadRepositories, loadUseCase, loadViewModel)
        }
    }
}