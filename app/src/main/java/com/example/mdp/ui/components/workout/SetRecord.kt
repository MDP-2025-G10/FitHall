package com.example.mdp.ui.components.workout


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mdp.firebase.firestore.model.Set

@Composable
fun SetRecord(workoutSets: List<Set>) {

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        workoutSets.forEach { set ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .drawBehind {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(set.id.toString())
                    Row {
                        Text(set.weight.toString())
                        Text(" kgs")
                    }
                    Row {
                        Text(set.reps.toString())
                        Text(" reps")
                    }
                }
            }
        }
    }
}