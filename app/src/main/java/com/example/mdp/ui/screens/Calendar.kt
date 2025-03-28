package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mdp.ui.components.toolbar.BottomBar
import com.example.mdp.ui.components.toolbar.TopBar


@Composable
fun Calendar(navController: NavController) {

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
            Text("Calendar Screen")
        }

    }
}