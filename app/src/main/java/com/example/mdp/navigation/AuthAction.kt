package com.example.mdp.navigation

sealed class AuthAction(val route: String) {
    data object Login : AuthAction(NavRoutes.RouteToLogin.route)
    data object Register : AuthAction(NavRoutes.RouteToRegister.route)
}