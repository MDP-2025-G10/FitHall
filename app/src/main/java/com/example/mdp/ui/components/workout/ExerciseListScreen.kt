package com.example.mdp.ui.components.workout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp.navigation.LocalExerciseViewModel
import com.example.mdp.wger.model.Exercise

@Composable
fun ExerciseListScreen(
    bodyPartId: Int,
    bodyPartName: String,
    onBack: () -> Unit
) {
    val exerciseViewModel = LocalExerciseViewModel.current
    val exercises: List<Exercise> by exerciseViewModel.exercises.collectAsState()

    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(bodyPartName) {
        exerciseViewModel.loadExercises(bodyPartName)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Back to Body Parts",
            modifier = Modifier
                .padding(8.dp)
                .clickable { onBack() }
        )

        Log.d("ExerciseListScreen", "$exercises")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {

            items(exercises) { exercise ->
                Card(
                    modifier = Modifier
                        .clickable { selectedExercise = exercise }
                        .width(400.dp)) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) { Text(text = exercise.name, modifier = Modifier.fillMaxWidth()) }
                }
            }
        }
    }
    selectedExercise?.let { exercise ->
        ExerciseDetailPopUp(
            exercise = exercise,
            onDismiss = { selectedExercise = null }
        )
    }
}
