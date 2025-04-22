package com.example.mdp.ui.components.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalWorkoutViewModel

@Composable
fun WorkoutSuggestion(navController: NavController) {
    val workoutViewModel = LocalWorkoutViewModel.current
    val workoutList by workoutViewModel.getallWorkouts.collectAsState(initial = emptyList())
    var isPopupVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Add a button to trigger the workout popup
        item {
            Button(
                onClick = { isPopupVisible = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text("Add Workout")
            }
        }

        // Add a header as a separate item
        item {
            Text(
                "Workout Suggestions",
                textAlign = TextAlign.Center
            )
        }

        // Add the list of items
        items(workoutList) { workoutItem ->
            WorkoutSuggestionCard(workoutItem)
        }

        // Optional: Add a footer or other content
        if (workoutList.isEmpty()) {
            item {
                Text(
                    "Try to search something?",
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Conditionally show the workout popup
    if (isPopupVisible) {
        WorkoutPopUP(
            onDismiss = { isPopupVisible = false }
        )
    }
}
