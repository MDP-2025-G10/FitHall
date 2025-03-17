package com.example.mdp.ui.components.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Ensure columns are equidistant
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Column for Calories
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp), // Add space between columns
                horizontalAlignment = Alignment.CenterHorizontally, // Center align the text
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Calories: ${calories.toInt()} kcal", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = calories, dailyAmountGoal = 2000f, size = 150.dp, color = Color.Red) // Smaller size
            }

            // Right Column for Fats, Proteins, and Carbs
            Column(
                modifier = Modifier.weight(1f),  // Take up remaining space
                horizontalAlignment = Alignment.CenterHorizontally, // Center align the text
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Fats: ${fats.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = fats, dailyAmountGoal = 100f, size = 70.dp, color = Color.Blue) // Smaller size
                Spacer(modifier = Modifier.height(8.dp)) // Add space between items

                Text(text = "Proteins: ${proteins.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = proteins, dailyAmountGoal = 75f, size = 70.dp, color = Color.Green) // Smaller size
                Spacer(modifier = Modifier.height(8.dp)) // Add space between items

                Text(text = "Carbs: ${carbs.toInt()} g", style = MaterialTheme.typography.bodyLarge)
                CircularProgressCard(amountsConsumed = carbs, dailyAmountGoal = 200f, size = 70.dp, color = Color.Yellow) // Smaller size
            }
        }
    }
}