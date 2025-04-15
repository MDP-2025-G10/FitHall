package com.example.mdp.ui.components.workout


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.data.model.Workout
import com.example.mdp.navigation.LocalDateViewModel
import com.example.mdp.navigation.LocalWorkoutViewModel
//import com.example.mdp.ui.components.profilepage.ProfilePageWorkoutCard



//make popup better
//

@Composable
fun WorkoutHistory(navController: NavController) {
    val workoutViewModel = LocalWorkoutViewModel.current
    val dateViewModel = LocalDateViewModel.current

    val selectedDate by dateViewModel.selectedDate
    val workouts by workoutViewModel.getallWorkouts.collectAsState(initial = emptyList())

    val workoutsForSelectedDate = workouts.filter { it.date == selectedDate.toString() }

    var selectedWorkout by remember { mutableStateOf<Workout?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        WorkoutDateSelector(navController)

        if (workoutsForSelectedDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutsForSelectedDate) { workout ->
                    // Make the existing card clickable
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .clickable { selectedWorkout = workout }
                    ) {
                        WorkoutHistoryCard(
                            workout = workout,
                            onDelete = {
                                workoutViewModel.deleteWorkout(workout)
                            }
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No workouts found for the selected date.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    // Show popup dialog with full workout details
    selectedWorkout?.let { workout ->
        WorkoutDetailsPopup(
            workout = workout,
            onDismiss = { selectedWorkout = null }
        )
    }
}

@Composable
fun WorkoutDetailsPopup(
    workout: Workout,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = workout.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "Date: ${workout.date}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Description: ${workout.description}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                items(workout.exercises) { exercise ->
                    Column(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Exercise: ${exercise.name}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        exercise.sets.forEachIndexed { index, set ->
                            Text(
                                text = "  Set ${index + 1}: ${set.reps} reps @ ${set.weight}kg",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun WorkoutHistoryCard(
    workout: Workout,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = workout.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Date: ${workout.date}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Description: ${workout.description}", style = MaterialTheme.typography.bodySmall)
            Button(
                onClick = onDelete,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Delete")
            }
        }
    }
}