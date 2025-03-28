package com.example.mdp.ui.components.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.data.model.Meal
import com.example.mdp.data.viewmodel.MealViewModel
import com.example.mdp.utils.formatTimestampToDate
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistorySection(
    navController: NavController,
    allMealList: List<Meal>,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DateSelector(navController)
        if (allMealList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allMealList) { meal ->
                    HistoryCard(meal)
                }
            }
        } else {
            Text("No history found.", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


@Composable
fun HistoryCard(meal: Meal) {
    var showPopup by remember { mutableStateOf(false) }
    val mealViewModel: MealViewModel = koinViewModel()
    val mealName = meal.name
    val calories = meal.calories
    val carbs = meal.carbs
    val protein = meal.proteins
    val fats = meal.fats
    val formattedDate = formatTimestampToDate(meal.timestamp)


    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(modifier = Modifier.weight(0.9f)) {
                Text(mealName, style = MaterialTheme.typography.titleMedium)
                Text("Calories: $calories kcal")
                Text("Carbs: $carbs g")
                Text("Protein: $protein g")
                Text("Fats: $fats g")
                Text("Time: $formattedDate")
            }
            Column(modifier = Modifier.weight(0.1f)) {
                IconButton(onClick = { showPopup = true }) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "add meal from history",
                        tint = Color(0xFF5A67B4),
                        modifier = Modifier.size(36.dp)
                    )
                }
                IconButton(onClick = { mealViewModel.deleteMeal(meal) }) {
                    Icon(
                        imageVector = Icons.Filled.RemoveCircle,
                        contentDescription = "Remove history",
                        tint = Color(0xFFFC2D00),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }

    if (showPopup) {
        FoodPopUp(meal) { showPopup = false }
    }
}