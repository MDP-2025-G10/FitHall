package com.example.mdp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Settings
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
import com.example.mdp.navigation.NavRoutes
import com.example.mdp.ui.components.dashboard.BottomBarIcon
import com.example.mdp.ui.components.dashboard.DailyIntakeProgressCard
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
//            MainScreenFoodCard()

            DailyIntakeProgressCard()


            // SingleMealCard(mealname = "Pizza")

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
    val currentRoute = navController.currentDestination?.route
    BottomAppBar(
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BottomBarIcon(
                navController,
                currentRoute,
                route = NavRoutes.RouteToHome.route,
                outlinedIcon = Icons.Outlined.Dashboard,
                roundedIcon = Icons.Rounded.Dashboard,
                description = "navigate to home"
            )

            BottomBarIcon(
                navController,
                currentRoute,
                route = NavRoutes.RouteToCamera.route,
                outlinedIcon = Icons.Outlined.CameraAlt,
                roundedIcon = Icons.Rounded.CameraAlt,
                description = "navigate to camera"
            )

            BottomBarIcon(
                navController,
                currentRoute,
                route = NavRoutes.RouteToCalendar.route,
                outlinedIcon = Icons.Outlined.CalendarMonth,
                roundedIcon = Icons.Rounded.CalendarMonth,
                description = "navigate to calendar"
            )

            BottomBarIcon(
                navController,
                currentRoute,
                route = NavRoutes.RouteToSetting.route,
                outlinedIcon = Icons.Outlined.Settings,
                roundedIcon = Icons.Rounded.Settings,
                description = "navigate to settings"
            )
        }
    }
}