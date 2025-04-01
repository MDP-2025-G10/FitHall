package com.example.mdp.notifications.workmanager

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager

// This class is used to initialize the WorkManager in the application
class MyApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this, workManagerConfiguration)
    }
    // This method is called when the WorkManager is initialized
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()
}