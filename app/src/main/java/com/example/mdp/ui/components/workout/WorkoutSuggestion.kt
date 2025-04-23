package com.example.mdp.ui.components.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


sealed class SuggestionScreenState {
    data object Main : SuggestionScreenState()
    data object BodyParts : SuggestionScreenState()
    data class ExerciseList(val bodyPartId: Int, val bodyPartName: String) : SuggestionScreenState()
}


@Composable
fun WorkoutSuggestion(navController: NavController) {
    var currentScreen by remember { mutableStateOf<SuggestionScreenState>(SuggestionScreenState.Main) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        when (val screen = currentScreen) {
            is SuggestionScreenState.Main -> {
                Card(
                    modifier = Modifier
                        .clickable { currentScreen = SuggestionScreenState.BodyParts }
                        .width(200.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                        Text(text = "Add a new workout")
                    }
                }
            }

            is SuggestionScreenState.BodyParts -> {
                BodyPartScreen(
                    onBack = { currentScreen = SuggestionScreenState.Main },
                    onBodyPartClick = { id, name ->
                        currentScreen = SuggestionScreenState.ExerciseList(id, name)
                    }
                )
            }

            is SuggestionScreenState.ExerciseList -> {
                ExerciseListScreen(
                    bodyPartId = screen.bodyPartId,
                    bodyPartName = screen.bodyPartName,
                    onBack = { currentScreen = SuggestionScreenState.BodyParts }
                )
            }
        }
    }

}


