package com.example.mdp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.R

@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = { TopBar(onNavigateToProfile = { navController.navigate("profile") }) },
        bottomBar = {
            BottomBar(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToCamera = { navController.navigate("camera") },
                onNavigateToCalendar = { navController.navigate("calendar") },
                onNavigateToSetting = { navController.navigate("setting") }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Home Screen")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onNavigateToProfile: () -> Unit) {

    TopAppBar(
        title = { Text("FitHall") },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo"
            )
        },
        actions = {
            IconButton(onClick = onNavigateToProfile) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "navigate to user profile",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
    )
}

@Composable
fun BottomBar(
    onNavigateToHome: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToSetting: () -> Unit,
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = onNavigateToHome) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "navigate to home",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = onNavigateToCamera) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = "navigate to camera",
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = onNavigateToCalendar) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "navigate to calendar",
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = onNavigateToSetting) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "navigate to settings",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}