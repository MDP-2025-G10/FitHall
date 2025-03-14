package com.example.mdp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DailyIntakeProgressCard() {
    // Hardcoded values for nutritional information
    val calories = 1000f
    val fats = 70f
    val proteins = 50f
    val carbs = 150f


    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), // Make the card fill the width
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Left Column for Calories
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp), // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally // Center align the text
            ) {
                Text(text = "Calories: ${calories.toInt()} kcal", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = calories, dailyAmountGoal = 2000f, size = 150.dp) // Smaller size
            }

            // Center Spacer
            Spacer(modifier = Modifier.width(16.dp)) // Add center padding between columns

            // Right Column for Fats, Proteins, and Carbs
            Column(
                modifier = Modifier.weight(1f) // Take up remaining space
            ) {
                Text(text = "Fats: ${fats.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = fats, dailyAmountGoal = 100f, size = 70.dp) // Smaller size
                Spacer(modifier = Modifier.height(8.dp)) // Add space between items

                Text(text = "Proteins: ${proteins.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = proteins, dailyAmountGoal = 75f, size = 70.dp) // Smaller size
                Spacer(modifier = Modifier.height(8.dp)) // Add space between items

                Text(text = "Carbs: ${carbs.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = carbs, dailyAmountGoal = 200f, size = 70.dp) // Smaller size
            }
        }
    }
}