package com.example.mdp.notifications.notificationutil.alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.mdp.MainActivity
import com.example.mdp.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Check if context and intent are non-null
        if (context != null && intent != null) {
            val reminderType = intent.getStringExtra("reminder_type") ?: ""
            val reminderMessage =
                intent.getStringExtra("reminder_message") ?: ""
            val notificationTitle = intent.getStringExtra("notification_title") ?: reminderType


            // Ensure the title and message are not default values
            if (notificationTitle.isNullOrBlank() || reminderMessage.isNullOrBlank() || notificationTitle == "Reminder" && reminderMessage == "Time to complete your task!") {
                Log.w("AlarmReceiver", "Skipped sending default or empty notification.")
                return // Do not send the notification
            }


            // Log that the alarm has been triggered
            Log.e("AlarmReceiver", "Alarm triggered at: ${System.currentTimeMillis()}")

            // Optionally display a toast for testing
            Toast.makeText(context, "Reminder triggered: $notificationTitle", Toast.LENGTH_SHORT)
                .show()

            // Create the notification
            showNotification(context, notificationTitle, reminderMessage)
        }
    }

    private fun showNotification(
        context: Context,
        notificationTitle: String,
        reminderMessage: String
    ) {
        val channelId = "general_reminder_channel"
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        // Create the notification channel (only for Android 8 and above)
        val channel = NotificationChannel(
            channelId,
            "General Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager?.createNotificationChannel(channel)

        // Open the app when the user taps the notification
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE // Required flag for Android 12 and above
        )

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(notificationTitle)
            .setContentText(reminderMessage)
            .setSmallIcon(R.drawable.logo) // Your notification icon
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Notify the user
        notificationManager?.notify(System.currentTimeMillis().toInt(), notification)
    }
}