package com.example.mdp

import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mdp.navigation.AppNavController
import com.example.mdp.notifications.notificationutil.notifications.NotificationHelper
//import com.example.mdp.notifications.notificationutil.notifications.NotificationWorker
import com.example.mdp.ui.theme.MDPTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        NotificationHelper.createNotificationChannel(this)
        checkAndRequestNotificationPermission()


        // Schedule initial work
//        val initialRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .setInitialDelay(1, TimeUnit.HOURS) // Adjust the delay as needed
//            .build()
//        WorkManager.getInstance(this).enqueue(initialRequest)


        setContent {
            MDPTheme {
                AppNavController(context = this)
            }
        }
    }
    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }
}


