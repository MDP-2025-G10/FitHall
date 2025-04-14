package com.example.mdp.ui.components.workout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mdp.data.model.Workout
import com.example.mdp.navigation.LocalWorkoutViewModel
//import com.example.mdp.ui.components.profilepage.ProfilePageWorkoutCard


@Composable
fun WorkoutHistoryCard() {
    val workoutViewModel = LocalWorkoutViewModel.current
    var selectedWorkout by remember { mutableStateOf<Workout?>(null) }

    // Display all the workouts in a card with lazy column on this page
    Card {
        val workouts by workoutViewModel.getallWorkouts.collectAsState(initial = emptyList())
        LazyColumn {
            items(workouts) { workout ->

                // Make the card clickable
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { selectedWorkout = workout }
                ) {
//                    ProfilePageWorkoutCard(workouts = workout)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        IconButton(onClick = {
                            workoutViewModel.deleteWorkout(workout)
                            Log.e("WorkoutHistoryCard", "Workout deleted: ${workout.id}")
                        }) {
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
    }

    // Workout detail dialog
    selectedWorkout?.let { workout ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { selectedWorkout = null },
            title = { Text(text = "Workout: ${workout.name}") },
            text = {
                LazyColumn {
                    item {
                        Text("Date: ${workout.date}")
                        Text("Description: ${workout.description}")
                    }
                    items(workout.exercises) { exercise ->
                        Text("Exercise: ${exercise.name}")
                        exercise.sets.forEachIndexed { index, set ->
                            Text("  Set ${index + 1}: ${set.reps} reps @ ${set.weight}kg")
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedWorkout = null }) {
                    Text("Close")
                }
            }
        )
    }
}
