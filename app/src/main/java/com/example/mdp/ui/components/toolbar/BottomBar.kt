package com.example.mdp.ui.components.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.NavRoutes


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