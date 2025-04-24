package com.example.mdp.ui.components.workout


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.navigation.LocalDateViewModel
import com.example.mdp.navigation.LocalMealViewModel
import com.example.mdp.navigation.LocalWorkoutViewModel
import com.example.mdp.navigation.NavRoutes
import com.example.mdp.ui.components.food.DateSelector

@Composable
fun WorkoutHistory(navController: NavController) {
    val workoutViewModel = LocalWorkoutViewModel.current
    val dateViewModel = LocalDateViewModel.current

    val selectedDate by dateViewModel.selectedDate

    var workoutForSelectedDate by remember { mutableStateOf(emptyList<Workout>()) }

    LaunchedEffect(selectedDate) {
        workoutViewModel.getWorkoutsForDate(selectedDate).collect { workouts ->
            workoutForSelectedDate = workouts
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        DateSelector(navController, NavRoutes.RouteToWorkout.route)
        if (workoutForSelectedDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutForSelectedDate) { workout ->
                    WorkoutHistoryCard(workout)
                }
            }

        } else {
            Text(
                "No workout history found.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun WorkoutHistoryCard(workout: Workout) {

    var showPopup by remember { mutableStateOf(false) }
    val workoutViewModel = LocalMealViewModel.current
    val workoutName = workout.name

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(workoutName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}