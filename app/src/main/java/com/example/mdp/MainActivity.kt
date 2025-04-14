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

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n     " +
            " which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n  " +
            "    contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n   " +
            "   testing, and allow receiving results in separate, testable classes independent from your\n   " +
            "   activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n   " +
            "   in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n    " +
            "  handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
            // You can now use the feature that requires the permission
        }
    }

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


