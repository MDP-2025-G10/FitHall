package com.example.mdp.ui.components.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp.wger.model.Exercise

@Composable
fun ExerciseDetailPopUp(exercise: Exercise, onDismiss: () -> Unit) {


//    val exercise = exercises.find { it.id == exerciseId.toIntOrNull() }

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

            }

//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                if (!exercise.imageUrl.isNullOrEmpty()) {
//                    AsyncImage(
//                        model = exercise.imageUrl,
//                        contentDescription = "Exercise Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(8.dp))
//                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
//                            .height(200.dp)
//                    )
//                } else {
//                    Text("No Image Available", color = Color.Gray)
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
////                Text(text = exercise.description ?: "No description available")
//            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
