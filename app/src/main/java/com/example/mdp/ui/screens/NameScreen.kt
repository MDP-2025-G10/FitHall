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
fun NameScreen(navController: NavController) {

    val authViewModel = LocalAuthViewModel.current
    val userViewModel = LocalUserViewModel.current

    val currentUser = authViewModel.currentUser.observeAsState().value
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("What's your name?")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                currentUser?.uid?.let { uid ->
                    userViewModel.updateUserName(uid, name)
                    navController.navigate("BodyInfoScreen")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}