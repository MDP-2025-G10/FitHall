package com.example.mdp.di


import com.example.mdp.firebase.auth.repository.AuthRepository
import com.example.mdp.firebase.auth.viewModel.AuthViewModel
import com.example.mdp.firebase.firestore.repository.MealRepository
import com.example.mdp.firebase.firestore.repository.UserRepository
import com.example.mdp.firebase.firestore.repository.WorkoutRepository
import com.example.mdp.firebase.firestore.viewModel.DateViewModel
import com.example.mdp.firebase.firestore.viewModel.MealViewModel
import com.example.mdp.firebase.firestore.viewModel.UserViewModel
import com.example.mdp.firebase.firestore.viewModel.WorkoutViewModel
import com.example.mdp.imgur.ImgurRetrofitInstance
import com.example.mdp.imgur.repository.ImgurRepository
import com.example.mdp.imgur.viewmodel.ImgurViewModel
import com.example.mdp.usda.USDARetrofitInstance
import com.example.mdp.usda.repository.FoodRepository
import com.example.mdp.usda.viewmodel.FoodViewModel
import com.example.mdp.wger.WgerRetrofitInstance
import com.example.mdp.wger.repository.ExerciseRepository
import com.example.mdp.wger.viewmodel.ExerciseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() } // Provide FirebaseAuth instance
    single { FirebaseFirestore.getInstance() } // Singleton instance of Firestore

    //  Inject FirebaseAuth and context into AuthRepository
    single { AuthRepository(get(), androidContext(), get()) }
    viewModel { AuthViewModel(get(), get()) }

    //  Workout
    single { WorkoutRepository(get(), get()) }
    viewModel { WorkoutViewModel(get()) }

    //  Meal
    single { MealRepository(get(), get()) }
    viewModel { MealViewModel(get()) }

    //  USDAApi
    single { USDARetrofitInstance.api }
    single { FoodRepository(get()) }
    viewModel { FoodViewModel(get()) }

    //  Date
    viewModel { DateViewModel() }

    //  ImgurApi
    single { ImgurRetrofitInstance.api }
    single { ImgurRepository(get()) }
    viewModel { ImgurViewModel(get()) }

    //  User
    single { UserRepository(get()) }
    viewModel { UserViewModel(get()) }

    //  Wger API
    single { WgerRetrofitInstance.api }
    single { ExerciseRepository(get()) }
    viewModel { ExerciseViewModel(get()) }
}