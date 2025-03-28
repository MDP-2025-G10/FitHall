package com.example.mdp.ui.components.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.data.viewmodel.MealViewModel
import com.example.mdp.navigation.NavRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun DailyIntakeProgressCard(navController: NavController) {
    val mealViewModel: MealViewModel = koinViewModel()
    val nutritionInfo by mealViewModel.todayNutrition.collectAsState()

    Log.d("nutritionInfo","$nutritionInfo")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(NavRoutes.RouteToNutrition.route) }
    ) {
        CaloriesBar(nutritionInfo.calories.toFloat(), 2000f)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CircularProgressCard(
                    amountsConsumed = nutritionInfo.fats.toFloat(),
                    dailyAmountGoal = 100f,
                    size = 100.dp,
                    color = Color(0xFFFF9B78),
                    type = "Fats"
                )
                CircularProgressCard(
                    amountsConsumed = nutritionInfo.proteins.toFloat(),
                    dailyAmountGoal = 75f,
                    size = 100.dp,
                    color = Color(0xFFFFD462),
                    type = "Carbs"
                )
                CircularProgressCard(
                    amountsConsumed = nutritionInfo.carbs.toFloat(),
                    dailyAmountGoal = 200f,
                    size = 100.dp,
                    color = Color(0xFF607ABF),
                    type = "Protein"
                )
            }
        }
    }
}