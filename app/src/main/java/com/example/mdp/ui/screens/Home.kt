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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.R
import com.example.mdp.ui.components.homepage.DailyIntakeProgressCard
import com.example.mdp.ui.components.homepage.MainScreenFoodCard
import com.example.mdp.navigation.NavRoutes
import com.example.mdp.ui.components.historylog.SingleMealCard
import com.example.mdp.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Home(navController: NavController, authViewModel: AuthViewModel = koinViewModel()) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                authViewModel
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Home Screen")

            MainScreenFoodCard()

            DailyIntakeProgressCard()


            //SingleMealCard(mealname = "Pizza")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("FitHall") },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo"
            )
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "User Profile",
                    modifier = Modifier.size(32.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(36.dp, 10.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Profile") },
                    onClick = {
                        expanded = false
                        navController.navigate(NavRoutes.RouteToProfile.route)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        authViewModel.logout()
                        expanded = false
//                        erase the navigation history including home.
                        navController.navigate(NavRoutes.RouteToLogin.route) {
                            popUpTo(NavRoutes.RouteToHome.route) { inclusive = true }
                        }
                    }
                )
            }
        },
    )
}

@Composable
fun BottomBar(
    navController: NavController
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = { navController.navigate(NavRoutes.RouteToHome.route) }) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "navigate to home",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { navController.navigate(NavRoutes.RouteToCamera.route) }) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = "navigate to camera",
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate(NavRoutes.RouteToCalendar.route) }) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "navigate to calendar",
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { navController.navigate(NavRoutes.RouteToSetting.route) }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "navigate to settings",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}