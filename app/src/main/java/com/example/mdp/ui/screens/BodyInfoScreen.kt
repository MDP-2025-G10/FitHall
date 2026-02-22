package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalAuthViewModel
import com.example.mdp.navigation.LocalUserViewModel

@Composable
fun BodyInfoScreen(navController: NavController) {

    val authViewModel = LocalAuthViewModel.current
    val userViewModel = LocalUserViewModel.current

    val currentUser = authViewModel.currentUser.observeAsState().value

    var gender by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Body Information")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender (male/female)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                currentUser?.uid?.let { uid ->
                    userViewModel.updateUserBodyInfo(
                        uid = uid,
                        gender = gender,
                        weight = weight.toFloatOrNull() ?: 0f,
                        height = height.toFloatOrNull() ?: 0f,
                        age = age.toIntOrNull() ?: 0
                    )

                    navController.navigate("home") {
                        popUpTo("name_step") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish")
        }
    }
}