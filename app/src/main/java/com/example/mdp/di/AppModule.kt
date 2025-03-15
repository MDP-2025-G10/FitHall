package com.example.mdp.di


import com.example.mdp.repository.AuthRepository
import com.example.mdp.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() } // Provide FirebaseAuth instance
    single { AuthRepository(get()) } //  Inject FirebaseAuth into AuthRepository
    viewModel { AuthViewModel(get()) } // Inject AuthRepository into AuthViewModel
}