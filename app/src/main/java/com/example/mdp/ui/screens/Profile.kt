package com.example.mdp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalAuthViewModel
import com.example.mdp.navigation.LocalMealViewModel
import com.example.mdp.navigation.LocalWorkoutViewModel
import com.example.mdp.ui.components.profilepage.ProfileCard
import com.example.mdp.ui.components.profilepage.ProfileFoodCard
import com.example.mdp.ui.components.profilepage.ProfilePageWorkoutCard
import com.example.mdp.ui.components.profilepage.bmiCalculator
import com.example.mdp.ui.components.profilepage.bmicomponent.viewmodel.BmiViewModel
import com.example.mdp.ui.components.toolbar.TopBar


//make use of lazy column  for all the cards ui elements

@Composable
fun Profile(navController: NavController) {
    val currentUser = LocalAuthViewModel.current.currentUser.observeAsState().value
    val mealViewModel = LocalMealViewModel.current
    val workoutViewModel = LocalWorkoutViewModel.current
    val bmiViewModel: BmiViewModel = viewModel()
    Scaffold(
        topBar = { TopBar(navController) },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {

            Text("Profile Screen")

            ProfileCard(
                profileName = currentUser?.displayName ?: "John Doe",
                handle = currentUser?.email ?: "johndoe",
                profileImageUrl = currentUser?.photoUrl?.toString()
            )

            Spacer(modifier = Modifier.padding(2.dp))

            // BMI calculator
            bmiCalculator(bmiViewModel = bmiViewModel)
/*
            Text(
                text = "Recently uploaded food",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge
            )
            val meals by mealViewModel.allMealList.collectAsState(initial = emptyList())
            LazyRow {
                items(meals) { meal ->
                    ProfileFoodCard(meal = meal)
                }
            }
*/
            Text(
                text = "Recently uploaded Workout",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge
            )
            val workouts by workoutViewModel.allWorkouts.observeAsState(initial = emptyList())
            LazyColumn {
                items(workouts) { workouts ->
                    ProfilePageWorkoutCard(workouts = workouts)
                }
            }
        }
    }
}