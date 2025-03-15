package com.example.mdp

import android.app.Application
import com.example.mdp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FitHallApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@FitHallApplication)
            // Load modules
            modules(appModule)
        }
    }
}