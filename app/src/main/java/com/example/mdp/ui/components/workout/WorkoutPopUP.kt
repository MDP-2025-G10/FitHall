package com.example.mdp.ui.components.workout


import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mdp.firebase.firestore.model.Exercise
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.firebase.firestore.model.WorkoutSet
import com.example.mdp.navigation.LocalWorkoutViewModel
import java.time.LocalDate
import java.util.Locale


//add a edit button on the workout history card

//add a window popup to to check for details about the workout

//the date field is not modifiable


data class ExerciseInputState(
    var name: String,
    var reps: String,
    var weight: String
) {
    var nameState = mutableStateOf(name)
    var repsState = mutableStateOf(reps)
    var weightState = mutableStateOf(weight)
}

@Composable
fun WorkoutScreen() {
    // State to control whether the workout popup is shown
    var isPopupVisible by remember { mutableStateOf(false) }

    // Trigger to show the workout popup
    Button(onClick = { isPopupVisible = true }) {
        Text("Add Workout")
    }

    // Conditionally show the workout popup
    if (isPopupVisible) {
        WorkoutPopUP(
            onDismiss = { isPopupVisible = false } // Close the popup when dismissed
        )
    }
}

@Composable
fun WorkoutPopUP(
    workout: Workout? = null,
    onDismiss: () -> Unit
) {
    val workoutViewModel = LocalWorkoutViewModel.current
    val currentDate = LocalDate.now().toString()

    // Workout-level fields
    var workoutName by remember { mutableStateOf(workout?.name ?: "") }
    var workoutDescription by remember { mutableStateOf(workout?.description ?: "") }
    var workoutDate by remember { mutableStateOf(workout?.date ?: currentDate) }

    // Maintain a dynamic list for the exercise inputs
    val exerciseStates = remember { mutableStateListOf<ExerciseInputState>() }
    if (workout != null && exerciseStates.isEmpty()) {
        workout.exercises.forEach { exercise ->
            val set = exercise.sets.firstOrNull() ?: WorkoutSet()
            exerciseStates.add(
                ExerciseInputState(
                    name = exercise.name,
                    reps = set.reps.toString(),
                    weight = set.weight.toString()
                )
            )
        }
    }
    if (exerciseStates.isEmpty()) {
        exerciseStates.add(ExerciseInputState(name = "", reps = "", weight = ""))
    }

    AlertDialog(
        onDismissRequest = onDismiss, // Close dialog when tapping outside
        title = { Text("Add Workout") },
        text = {
            Column {
                OutlinedTextField(
                    value = workoutName,
                    onValueChange = { workoutName = it },
                    label = { Text("Workout Name") }
                )
                OutlinedTextField(
                    value = workoutDescription,
                    onValueChange = { workoutDescription = it },
                    label = { Text("Workout Description") }
                )
                DateInputField(
                    label = "Workout Date",
                    dateValue = workoutDate,
                    onDateChange = { workoutDate = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Render all exercise inputs dynamically
                exerciseStates.forEachIndexed { index, exerciseInput ->
                    ExerciseInput(
                        exerciseInput = exerciseInput,
                        onDelete = { exerciseStates.removeAt(index) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            exerciseStates.add(
                                ExerciseInputState(
                                    name = "",
                                    reps = "",
                                    weight = ""
                                )
                            )
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Exercise")
                    }

                    IconButton(
                        onClick = {
                            if (exerciseStates.size > 1) {
                                exerciseStates.removeAt(exerciseStates.lastIndex)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Remove Exercise")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // Create the workout object
                val exercises = exerciseStates.map { input ->
                    Exercise(
                        name = if (input.nameState.value.isBlank()) "Exercise Name" else input.nameState.value,
                        sets = listOf(
                            WorkoutSet(
                                reps = input.repsState.value.toIntOrNull() ?: 0, // Parse reps to integer
                                weight = input.weightState.value.toIntOrNull() ?: 0 // Parse weight to integer
                            )
                        )
                    )
                }
                val newWorkout = Workout(
                    name = workoutName,
                    description = workoutDescription,
                    date = workoutDate,
                    exercises = exercises
                )

                // Insert the new workout into the ViewModel
                workoutViewModel.insertWorkout(newWorkout)

                // Dismiss the popup after workout is added
                onDismiss()
            }) {
                Text("Confirm")
            }
        }
    )
}

@Composable
fun ExerciseInput(
    exerciseInput: ExerciseInputState,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = exerciseInput.nameState.value,
                onValueChange = { exerciseInput.nameState.value = it },
                label = { Text("Exercise Name") }
            )
            OutlinedTextField(
                value = exerciseInput.repsState.value,
                onValueChange = { exerciseInput.repsState.value = it },
                label = { Text("Reps") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = exerciseInput.weightState.value,
                onValueChange = { exerciseInput.weightState.value = it },
                label = { Text("Weight") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        IconButton(
            onClick = onDelete,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Exercise",
                tint = Color.Red
            )
        }
    }
}

@Composable
fun DateInputField(
    label: String,
    dateValue: String,
    onDateChange: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    // Split current date value if editable
    val (year, month, day) = dateValue.split("-").map { it.toIntOrNull() ?: 0 }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formatted = String.format(
                    Locale.GERMANY,
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                onDateChange(formatted)
            },
            year,
            month - 1,
            day
        )
    }

    OutlinedTextField(
        value = dateValue,
        onValueChange = {},
        label = { Text(label) },
        readOnly = false,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() }
    )
}