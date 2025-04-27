package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp.firebase.firestore.viewModel.Nutrition

@Composable
fun NutritionCard(nutrition: Nutrition?) {
    if (nutrition == null) return

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Food: ${nutrition.label}", style = MaterialTheme.typography.titleMedium)
        Text("Calories: ${nutrition.calories}")
        Text("Protein: ${nutrition.protein}g")
        Text("Carbs: ${nutrition.carbohydrates}g")
        Text("Fats: ${nutrition.fats}g")
    }
}