package com.example.mdp.ui.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp.data.model.Workouts

@Composable
fun ProfilePageWorkoutCard(workouts: Workouts) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Column{
//            Text(text = workouts.date,
//                fontSize = 14.sp,
//                style = MaterialTheme.typography.titleMedium
//            )
            }

            Column {
                Text(
                    text = workouts.name,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = workouts.description,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}