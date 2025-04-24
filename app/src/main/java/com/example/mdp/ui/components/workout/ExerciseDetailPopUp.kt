package com.example.mdp.ui.components.workout

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.navigation.LocalWorkoutViewModel
import com.example.mdp.wger.model.Exercise

@Composable
fun ExerciseDetailPopUp(exercise: Exercise, onDismiss: () -> Unit) {

    val workoutViewModel = LocalWorkoutViewModel.current
    val workoutName by remember { mutableStateOf(exercise.name) }
    val workoutDescription by remember { mutableStateOf(exercise.description) }

    val weightInput by workoutViewModel.weightInput.collectAsState()
    val repsInput by workoutViewModel.repsInput.collectAsState()
    val sets by workoutViewModel.sets.collectAsState()

    Log.d("ExerciseDetailPopUp", "$exercise")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(exercise.name) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                WorkoutControl(
                    weightInput,
                    repsInput,
                    { workoutViewModel.setWeightInput(it) },
                    { workoutViewModel.setRepsInput(it) },
                    {
                        workoutViewModel.addSetToWorkout(exercise.name)

                    }
                )
                if (exercise.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = exercise.imageUrl,
                        contentDescription = "Exercise Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .height(200.dp)
                    )
                } else {
                    Text("No Image Available", color = Color.Gray)
                }
                if (sets.isNotEmpty()) {
                    Text("Sets:")
                    sets.forEachIndexed { index, set ->
                        Text("Set ${index + 1}: ${set.weight} kg x ${set.reps} reps")
                    }
                }
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    val newWorkout = Workout(
                        id = "",
                        name = workoutName,
                        description = workoutDescription,
                        imagePath = exercise.imageUrl,
                        sets = sets,
                        timestamp = System.currentTimeMillis()
                    )
                    workoutViewModel.insertWorkout(newWorkout)
                    onDismiss()
                },
                enabled = workoutName.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
