package com.example.mdp.ui.components.food

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mdp.data.model.Meal
import com.example.mdp.navigation.LocalDateViewModel
import com.example.mdp.navigation.LocalMealViewModel
import com.example.mdp.utils.historyCardTimeFormatter


@Composable
fun HistorySection(navController: NavController) {
    val dateViewModel = LocalDateViewModel.current
    val mealViewModel = LocalMealViewModel.current

    val selectedDate by dateViewModel.selectedDate

    var mealsForSelectedDate by remember { mutableStateOf(emptyList<Meal>()) }

    LaunchedEffect(selectedDate) {
        mealViewModel.getMealsForDate(selectedDate).collect { meals ->
            mealsForSelectedDate = meals
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DateSelector(navController)

        if (mealsForSelectedDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mealsForSelectedDate) { meal ->
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
    val mealViewModel = LocalMealViewModel.current
    val mealName = meal.name
    val calories = meal.calories
    val carbs = meal.carbs
    val protein = meal.proteins
    val fats = meal.fats
    val imagePath = meal.imagePath
    val formattedDate = historyCardTimeFormatter(meal.timestamp)

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
//                Text("Carbs: $carbs g")
//                Text("Protein: $protein g")
//                Text("Fats: $fats g")
                MacroProgressBar(carbs, protein, fats)
                AsyncImage(
                    model = imagePath,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Text(formattedDate)
                    IconButton(onClick = { showPopup = true }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "add meal from history",
                            tint = Color(0xFF5A67B4),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = { mealViewModel.deleteMeal(meal) }) {
                        Icon(
                            imageVector = Icons.Filled.RemoveCircle,
                            contentDescription = "Remove history",
                            tint = Color(0xFFAA5559),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }

    if (showPopup) {
        FoodPopUp(meal) { showPopup = false }
    }
}