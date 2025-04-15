package com.example.mdp.ui.components.workout


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mdp.firebase.firestore.model.Workout

@Composable
fun WorkoutSuggestionCard(workout: Workout) {

    var showPopup by remember { mutableStateOf(false) }

    if (showPopup) {
        WorkoutPopUP(workout) { showPopup = false }
    }
}
