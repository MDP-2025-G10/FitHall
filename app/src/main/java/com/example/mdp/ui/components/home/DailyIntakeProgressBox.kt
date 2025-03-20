package com.example.mdp.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.ui.window.MealPopup
import com.example.mdp.viewmodels.MealViewModel

@Composable
fun DailyIntakeProgressCard(navController: NavController, mealViewModel: MealViewModel) {
    // Hardcoded values for nutritional information
    val calories = 1000f
    val fats = 70f
    val proteins = 50f
    val carbs = 150f
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    ) {
        CaloriesBar(1300f, 2000f)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                CircularProgressCard(
                    amountsConsumed = fats,
                    dailyAmountGoal = 100f,
                    size = 100.dp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    type = "Fats"
                )
                CircularProgressCard(
                    amountsConsumed = proteins,
                    dailyAmountGoal = 75f,
                    size = 100.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    type = "Carbs"
                )
                CircularProgressCard(
                    amountsConsumed = carbs,
                    dailyAmountGoal = 200f,
                    size = 100.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    type = "Protein"
                )
            }
        }
    }

    if (showDialog) {
        MealPopup(mealViewModel = mealViewModel, onDismiss = { showDialog = false })
    }
}