package com.example.mdp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mdp.navigation.AppNavController
import com.example.mdp.ui.theme.MDPTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)?.let {
            Log.d("FirebaseCheck", "Firebase is initialized successfully!")
        } ?: Log.e("FirebaseCheck", "Firebase failed to initialize!")

        setContent {
            MDPTheme {
                AppNavController()
            }
        }
    }
}
