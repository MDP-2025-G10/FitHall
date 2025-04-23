package com.example.mdp.ui.components.workout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalExerciseViewModel


@Composable
fun BodyPartScreen(navController: NavController, onBack: () -> Unit) {
    val exerciseViewModel = LocalExerciseViewModel.current
    val bodyParts by exerciseViewModel.bodyParts.collectAsState()

    LaunchedEffect(Unit) {
        exerciseViewModel.loadBodyParts()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Log.d("BodyPart", "$bodyParts")
        bodyParts.forEach { bodyPart ->
            BodyPartCardTemplate(
                navController = navController,
//                route = NavRoutes.routeToExerciseList(bodyPart.name),
                bodyPart = bodyPart.name
            )
        }
    }

}

@Composable
fun BodyPartCardTemplate(
    navController: NavController,
//    route: String,
    bodyPart: String? = null
) {
    Card(modifier = Modifier
//        .clickable { navController.navigate(route) }
        .width(200.dp)) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { Text(text = bodyPart ?: "Favorites") }
    }
}