package com.example.mdp.navigation

sealed class NavRoutes(val route: String) {

    data object RouteToHome : NavRoutes("home")
    data object RouteToProfile : NavRoutes("profile")
    data object RouteToCamera : NavRoutes("camera")
    data object RouteToCalendar : NavRoutes("calendar")
    data object RouteToSetting : NavRoutes("setting")

    companion object {
        fun routeToMain(date: String): String = "main/$date"
        fun routeToExerciseList(bodyPart: String): String = "exercise/bodyPart/$bodyPart"
        fun routeToExerciseDetail(exerciseId: String): String = "exerciseDetail/$exerciseId"
    }
}