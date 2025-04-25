package com.example.mdp.ui.components.workout


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.navigation.LocalDateViewModel
import com.example.mdp.navigation.LocalWorkoutViewModel
import com.example.mdp.navigation.NavRoutes
import com.example.mdp.ui.components.food.DateSelector
import com.example.mdp.utils.historyCardTimeFormatter

@Composable
fun WorkoutHistory(navController: NavController) {
    val workoutViewModel = LocalWorkoutViewModel.current
    val dateViewModel = LocalDateViewModel.current

    val selectedDate by dateViewModel.selectedDate

    var workoutForSelectedDate by remember { mutableStateOf(emptyList<Workout>()) }

    LaunchedEffect(selectedDate) {
        workoutViewModel.getWorkoutsForDate(selectedDate).collect { workouts ->
            workoutForSelectedDate = workouts
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        DateSelector(navController, NavRoutes.RouteToWorkout.route)
        if (workoutForSelectedDate.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workoutForSelectedDate) { workout ->
                    WorkoutHistoryCard(workout)
                }
            }

        } else {
            Text(
                "No workout history found.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun WorkoutHistoryCard(workout: Workout) {

    var showPopup by remember { mutableStateOf(false) }
    val workoutViewModel = LocalWorkoutViewModel.current
    val name = workout.name
    val description = workout.description
    val imagePath = workout.imagePath
    val formattedDate = historyCardTimeFormatter(workout.timestamp)
    val sets = workout.sets
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(description)
            if (imagePath.isNotEmpty()) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("No Image Available", color = Color.Gray)
            }

            SetRecord(sets)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Text(formattedDate)
                IconButton(onClick = { showPopup = true }) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "add workout from history",
                        tint = Color(0xFF5A67B4),
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { workoutViewModel.deleteWorkout(workout) }) {
                    Icon(
                        imageVector = Icons.Filled.RemoveCircle,
                        contentDescription = "Remove history",
                        tint = Color(0xFFAA5559),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

    }
}