package com.example.mdp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Switch
import androidx.compose.material3.Slider
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mdp.ui.components.toolbar.BottomBar
import com.example.mdp.ui.components.toolbar.TopBar
import com.example.mdp.notifications.notificationutil.preferences.PreferencesHelper
import androidx.navigation.NavController
import com.example.mdp.notifications.notificationutil.alarms.AlarmScheduler

@androidx.annotation.RequiresPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM)
@Composable
fun Setting(navController: NavController) {

    val sharedPreferences = navController.context.getSharedPreferences(
        "AppPreferences",
        android.content.Context.MODE_PRIVATE
    )

    // State to hold the switch and seekbar values
    var isReminderEnabled by remember {
        mutableStateOf(
            PreferencesHelper.isReminderEnabled(
                navController.context
            )
        )
    }
    var reminderInterval by remember {
        mutableStateOf(
            PreferencesHelper.getReminderInterval(
                navController.context
            )
        )
    }

    var customMessage by remember {
        mutableStateOf(
            TextFieldValue(
                sharedPreferences.getString(
                    "custom_message",
                    "Time to drink water!"
                ) ?: "Time to drink water!"
            )
        )
    }


    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)

        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Reminder Switch to enable or disable reminder
                    Text(text = "Enable Reminder", style = MaterialTheme.typography.titleMedium)
                    Switch(
                        checked = isReminderEnabled,
                        onCheckedChange = { checked ->
                            isReminderEnabled = checked
                            PreferencesHelper.setReminderEnabled(navController.context, checked)
                            if (checked) {
                                // Schedule reminder if enabled
                                AlarmScheduler.scheduleAlarms(
                                    context = navController.context,
                                    reminderType = "Water Reminder",
                                    reminderMessage = customMessage.text,
                                    intervalMillis = reminderInterval * 60 * 60 * 1000L  // Convert hours to milliseconds
                                )
                            } else {
                                // Cancel reminder if disabled
                                AlarmScheduler.cancelAlarms(navController.context)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reminder interval slider
                    Text(
                        text = "Set reminder interval (in hours): $reminderInterval hours",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Slider(
                        value = reminderInterval.toFloat(),
                        onValueChange = { newValue ->
                            reminderInterval = newValue.toInt()
                        },
                        valueRange = 1f..24f,
                        steps = 23, // 24 hours interval
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // TextField for custom reminder message
                    Text(
                        text = "Custom Reminder Message",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextField(
                        value = customMessage,
                        onValueChange = { newMessage ->
                            customMessage = newMessage
                            Log.e("customMessage", customMessage.text)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        label = { Text("Enter your reminder message here") }
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Save Button
                    Button(
                        onClick = {
                            // Save the updated reminder interval and custom message in Preferences
                            with(sharedPreferences.edit()) {
                                putString("custom_message", customMessage.text)
                                apply()
                            }
                            PreferencesHelper.saveReminderInterval(
                                navController.context,
                                reminderInterval
                            )
                            Toast.makeText(
                                navController.context,
                                "Settings saved!",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            if (isReminderEnabled) {
                                // Reschedule the reminder with new interval and custom message if enabled
                                AlarmScheduler.scheduleAlarms(
                                    context = navController.context,
                                    reminderType = "Water Reminder",
                                    reminderMessage = customMessage.text,
                                    intervalMillis = reminderInterval * 60 * 60 * 1000L  // Convert hours to milliseconds
                                )
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Save Settings")
                    }
                }
            }
        }
    }
}