package com.example.mdp.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mdp.data.viewmodel.DateViewModel
import com.example.mdp.data.viewmodel.MealViewModel
import com.example.mdp.data.viewmodel.WorkoutViewModel
import com.example.mdp.firebase.auth.viewModel.AuthViewModel
import com.example.mdp.ui.screens.Auth
import com.example.mdp.ui.screens.Calendar
import com.example.mdp.ui.screens.Camera
import com.example.mdp.ui.screens.Food
import com.example.mdp.ui.screens.Home
import com.example.mdp.ui.screens.Nutrition
import com.example.mdp.ui.screens.Profile
import com.example.mdp.ui.screens.Setting
import com.example.mdp.ui.screens.Workout
import com.example.mdp.usda.viewmodel.FoodViewModel
import org.koin.androidx.compose.koinViewModel

val LocalDateViewModel = compositionLocalOf<DateViewModel> { error("No DateViewModel provided") }
val LocalAuthViewModel = compositionLocalOf<AuthViewModel> { error("No AuthViewModel provided") }
val LocalMealViewModel = compositionLocalOf<MealViewModel> { error("No MealViewModel provided") }
val LocalFoodViewModel = compositionLocalOf<FoodViewModel> { error("No FoodViewModel provided") }
val LocalWorkoutViewModel =
    compositionLocalOf<WorkoutViewModel> { error("No WorkoutViewModel provided") }

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavController() {

    val navController = rememberNavController()

    val authViewModel: AuthViewModel = koinViewModel()
    val dateViewModel: DateViewModel = koinViewModel()
    val mealViewModel: MealViewModel = koinViewModel()
    val foodViewModel: FoodViewModel = koinViewModel()
    val workoutViewModel: WorkoutViewModel = koinViewModel()

    val currentUser by authViewModel.currentUser.observeAsState()

    Log.d("AppNavController", "Current user: $currentUser")

    CompositionLocalProvider(
        LocalDateViewModel provides dateViewModel,
        LocalAuthViewModel provides authViewModel,
        LocalMealViewModel provides mealViewModel,
        LocalFoodViewModel provides foodViewModel,
        LocalWorkoutViewModel provides workoutViewModel,
    ) {
        NavHost(
            navController = navController,
            startDestination =
            if (currentUser == null) NavRoutes.RouteToLogin.route
            else NavRoutes.RouteToHome.route
        ) {
            composable(
                route = NavRoutes.RouteToLogin.route,
            ) { Auth(navController, isLogin = true) }

            composable(
                route = NavRoutes.RouteToRegister.route,
            ) { Auth(navController, isLogin = false) }

            composable(
                route = NavRoutes.RouteToHome.route,
            ) { Home(navController) }

            composable(
                route = NavRoutes.RouteToFood.route,
            ) { Food(navController) }

            composable(
                route = NavRoutes.RouteToNutrition.route,
            ) { Nutrition(navController) }

            composable(
                route = NavRoutes.RouteToWorkout.route,
            ) { Workout(navController) }

            composable(
                route = NavRoutes.RouteToCamera.route,
            ) { Camera(navController) }

            composable(
                route = NavRoutes.RouteToCalendar.route,
            ) { Calendar(navController) }

            composable(
                route = NavRoutes.RouteToSetting.route,
            ) { Setting(navController) }

            composable(
                route = NavRoutes.RouteToProfile.route,
                enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
            ) { Profile(navController) }

        }
    }

}