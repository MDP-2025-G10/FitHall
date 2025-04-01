package com.example.mdp.notifications.notificationutil.preferences


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


object PreferencesHelper {
    private const val PREFS_NAME = "settings_prefs"
    private const val REMINDER_ENABLED_KEY = "reminder_enabled"
    private const val REMINDER_INTERVAL_KEY = "reminder_interval"

    // SharedPreferences instance
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Function to get the current reminder interval
    fun isReminderEnabled(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(REMINDER_ENABLED_KEY, false)
    }

    // Function to save the reminder enabled state
    fun setReminderEnabled(context: Context, isEnabled: Boolean) {
        getSharedPreferences(context).edit() { putBoolean(REMINDER_ENABLED_KEY, isEnabled) }
    }

    // Function to get the current reminder interval
    fun getReminderInterval(context: Context): Int {
        return getSharedPreferences(context).getInt(REMINDER_INTERVAL_KEY, 2) // Default to 2 hours
    }

    // Function to save the reminder interval
    fun saveReminderInterval(context: Context, intervalInHours: Int) {
        getSharedPreferences(context).edit() { putInt(REMINDER_INTERVAL_KEY, intervalInHours) }
    }
}