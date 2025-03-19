package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mdp.ui.components.profilepage.ProfileFoodCard
import com.example.mdp.ui.components.profilepage.ProfilePageWorkoutCard
import com.example.mdp.viewmodels.MealViewModel
import com.example.mdp.viewmodels.WorkoutViewModel




@Composable
fun Profile(navController: NavController,mealViewModel: MealViewModel = viewModel(),workoutViewModel: WorkoutViewModel = viewModel()) {
    //insert test data into the database
    LaunchedEffect(Unit) {
        mealViewModel.insertTestMeal()
        workoutViewModel.insertTestWorkout()
    }

    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Profile Screen")

            Column {
                Text(
                    text = "Recently uploaded food",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                val meals by mealViewModel.allMeals.observeAsState(initial = emptyList())
                LazyRow {
                    items(meals) { meal ->
                        ProfileFoodCard(meal = meal)
                    }
                }

                Text(
                    text = "Recently uploaded Workout",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                val workouts by workoutViewModel.allWorkouts.observeAsState(initial = emptyList())
                LazyColumn {
                    items(workouts) {  workouts ->
                        ProfilePageWorkoutCard(workouts = workouts)
                    }
                }
            }
        }
    }
}