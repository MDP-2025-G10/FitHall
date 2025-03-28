package com.example.mdp.di


import com.example.mdp.data.database.WorkoutDatabase
import com.example.mdp.data.repository.MealRepository
import com.example.mdp.data.repository.WorkoutRepository
import com.example.mdp.data.viewmodel.MealViewModel
import com.example.mdp.data.viewmodel.WorkoutViewModel
import com.example.mdp.firebase.auth.repository.AuthRepository
import com.example.mdp.firebase.auth.viewModel.AuthViewModel
import com.example.mdp.usda.RetrofitInstance
import com.example.mdp.usda.repository.FoodRepository
import com.example.mdp.usda.viewmodel.FoodViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() } // Provide FirebaseAuth instance
    single { FirebaseFirestore.getInstance() } // Singleton instance of Firestore
    single { MealRepository(get(), get()) }
    single {
        AuthRepository(
            get(),
            androidContext()
        )
    }   //  Inject FirebaseAuth and context into AuthRepository
    viewModel { AuthViewModel(get()) }  // Inject AuthRepository into AuthViewModel

    single { WorkoutDatabase.getDatabase(get()).workoutDao() }
    single { WorkoutRepository(get()) }
    viewModel { WorkoutViewModel(get()) }

//    single { MealDatabase.getDatabase(get()).mealDao() }

    viewModel { MealViewModel(get()) }

    // USDAApiService
    single { RetrofitInstance.api }
    single { FoodRepository(get()) }
    viewModel { FoodViewModel(get()) }

}