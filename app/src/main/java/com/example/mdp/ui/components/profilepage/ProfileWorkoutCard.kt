package com.example.mdp.ui.components.profilepage

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun  ProfilePageWorkoutCard(workoutname: String, description: String) {
    Card(
        modifier =  Modifier
            .fillMaxWidth()
            .padding(8.dp)

        ) {
        Text(
            workoutname,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge
            )

        Text(
            text = description,
            fontSize = 14.sp,
            style = MaterialTheme.typography.titleMedium)

    }
}