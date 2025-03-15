package com.example.mdp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.mdp.navigation.AppNavController
import com.example.mdp.ui.theme.MDPTheme
import com.example.mdp.viewmodels.AuthViewModel
import com.google.firebase.FirebaseApp
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        FirebaseApp.initializeApp(this)?.let {
//            Log.d("FirebaseCheck", "Firebase is initialized successfully!")
//        } ?: Log.e("FirebaseCheck", "Firebase failed to initialize!")

        setContent {
            MDPTheme {
                val authViewModel: AuthViewModel = koinViewModel() // Use koinViewModel() to get the instance
                authViewModel.logout() // This should set the currentUser to null
                AppNavController()
            }
        }
    }
}
