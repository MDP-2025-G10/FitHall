package com.example.mdp.notifications.notificationutil.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mdp.R


/**
 * NotificationHelper is a utility class for managing notifications in the app.
 * It provides methods to create notification channels and send notifications.
 */
object NotificationHelper {

    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_ID = "mdp_notifications"
    private const val CHANNEL_NAME = "General Notifications"
    private const val CHANNEL_DESC = "Notifications for app updates and reminders"

    /// Function to create a notification channel
    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESC
        }
        // Create the notification channel
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /// Function to send a notification
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun sendNotification(context: Context, title: String, message: String, notificationid: Int) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationid, builder.build())
        }
    }
}