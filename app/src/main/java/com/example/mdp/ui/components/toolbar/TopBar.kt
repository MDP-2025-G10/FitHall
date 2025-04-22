package com.example.mdp.ui.components.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.R
import com.example.mdp.navigation.LocalAuthViewModel
import com.example.mdp.navigation.NavRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val authViewModel = LocalAuthViewModel.current
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.padding(10.dp),
        title = { Text("FitHall") },
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
                        navController.navigate(NavRoutes.RouteToLogin.route) {
                            popUpTo(NavRoutes.RouteToHome.route) { inclusive = true }
                        }
                    }
                )
            }
        },
    )
}