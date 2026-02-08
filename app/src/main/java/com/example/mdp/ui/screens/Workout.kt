package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalExerciseViewModel
import com.example.mdp.ui.components.toolbar.BottomBar
import com.example.mdp.ui.components.toolbar.TopBar
import com.example.mdp.ui.components.workout.WorkoutHistory
import com.example.mdp.ui.components.workout.WorkoutSuggestion


@Composable
fun Workout(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val exerciseViewModel = LocalExerciseViewModel.current

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val tabs = listOf("History", "Suggestions")
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            exerciseViewModel.updateSearchQuery("")
                        },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> WorkoutHistory(navController)
                1 -> WorkoutSuggestion()
            }
        }
    }
}

