package com.example.mdp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalUserViewModel
import com.example.mdp.ui.components.profile.AccountInfoCard
import com.example.mdp.ui.components.profile.BMICard
import com.example.mdp.ui.components.profile.BodyInfoCard
import com.example.mdp.ui.components.toolbar.BottomBar
import com.example.mdp.ui.components.toolbar.TopBar


@Composable
fun Profile(navController: NavController) {

    val userViewModel = LocalUserViewModel.current
    val user = userViewModel.user.collectAsState().value

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            AccountInfoCard(user = user, onUpdateUser = { updatedUser ->
                userViewModel.updateUser(updatedUser)
            })
            BodyInfoCard(user = user, onUpdateUser = { updatedUser ->
                userViewModel.updateUser(updatedUser)
            })
            BMICard(user = user)
        }
    }
}