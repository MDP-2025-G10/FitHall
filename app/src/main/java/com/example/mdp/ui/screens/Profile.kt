package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.ui.components.profilepage.ProfileFoodCard
import com.example.mdp.ui.components.profilepage.ProfilePageWorkoutCard

//data class for food items
data class FoodItem(val name: String, val date: String)
//data class for workout items
data class WorkoutItem(val workoutname: String, val description: String)

//values for food items
val foodItems = listOf(
    FoodItem("Pizza", "2021-10-10"),
    FoodItem("Burger", "2021-10-11"),
    FoodItem("Salad", "2021-10-12"),
    FoodItem("Fried Chicken", "2021-10-13"),
    FoodItem("Sushi", "2021-10-14"),
    FoodItem("Pasta", "2021-10-15"),
    FoodItem("Rice", "2021-10-16"),
    FoodItem("Noodles", "2021-10-17"),
    FoodItem("Sandwich", "2021-10-18"),
    FoodItem("Hotdog", "2021-10-19"),
)
//values workout items
val workoutItems = listOf(
    WorkoutItem("Pushups", "10 reps"),
    WorkoutItem("Situps", "20 reps"),
    WorkoutItem("Squats", "15 reps"),
    WorkoutItem("Lunges", "10 reps"),
    WorkoutItem("Plank", "30 seconds"),
    WorkoutItem("Jumping Jacks", "20 reps"),
    WorkoutItem("Burpees", "10 reps"),
    WorkoutItem("Mountain Climbers", "20 reps"),
    WorkoutItem("Leg Raises", "15 reps"),
    WorkoutItem("Crunches", "20 reps"),
)

//composable for food items list  for profile page,
// pissibly will be moved to another file
@Composable
fun FoodItemList(foodItems: List<FoodItem>) {
    LazyRow {
        items(foodItems) { foodItem ->
            ProfileFoodCard(foodname = foodItem.name, date = foodItem.date)
        }
    }
}
//composable for workout items list for profile page,
// pissibly will be moved to another file

@Composable
fun WorkoutItemList(workoutItems: List<WorkoutItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
        ,) {
        items(workoutItems) { workoutItem ->
            ProfilePageWorkoutCard(workoutname = workoutItem.workoutname, description = workoutItem.description)
        }
    }
}

@Composable
fun Profile(navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Profile Screen")

            Column(){
                // will have to implement a way to limit the amount of  items
                //that are displayed on the profile page to a week/7 days
                Text(
                    text = "Recently uploaded food ",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                FoodItemList(foodItems)

                Text(
                    text = "Recently uploaded Workout ",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )


                WorkoutItemList(workoutItems)


            }
        }

    }
}