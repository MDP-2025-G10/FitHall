package com.example.mdp.notifications.calorienotificaton

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import com.example.mdp.navigation.LocalMealViewModel
import com.example.mdp.notifications.notificationutil.notifications.NotificationHelper

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
@Composable
/// This function is responsible for sending notifications when the user exceeds their daily calorie goal.
fun IntakeNotification(totalCalories: Int, dailyGoal: Int) {
    val mealViewModel = LocalMealViewModel.current
    val calorieHistory by mealViewModel.calorieHistory.collectAsState()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)

    LaunchedEffect(calorieHistory) {
        // Check if the calorie history is not empty and if the last entry is different from the last notified calories
        if (calorieHistory.isNotEmpty()) {
            val lastEntry = calorieHistory.last()
            val lastNotificationTime = sharedPreferences.getLong("lastNotificationTime", 0L)
            val lastNotifiedCalories = sharedPreferences.getInt("lastNotifiedCalories", -1)

            if (System.currentTimeMillis() > lastNotificationTime && lastEntry.totalCalories != lastNotifiedCalories) {
                val message = if (totalCalories > dailyGoal) {
                    val exceededBy = totalCalories - dailyGoal
                    "You have exceeded your daily goal by $exceededBy calories! Total calories: $totalCalories"
                } else {
                    "Food with ${lastEntry.totalCalories} calories has been added to your tracked calories."
                }
                NotificationHelper.sendNotification(
                    context = context,
                    title = "Calorie Tracker",
                    message = message,
                    notificationid = 3
                )

                // Update the last notification time and last notified calories
                sharedPreferences.edit() {
                    putLong("lastNotificationTime", System.currentTimeMillis())
                        .putInt("lastNotifiedCalories", lastEntry.totalCalories)
                }
            }
        }
    }
}