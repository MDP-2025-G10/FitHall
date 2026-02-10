package com.example.mdp.firebase.firestore.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.mdp.ui.components.utils.FoodRecognitionLabels
import com.example.mdp.ui.components.utils.NutritionUtils

// NOTE:
// This file previously contained a duplicate FoodViewModel implementation
// under the firebase.firestore.viewModel package. It has been removed
// to avoid confusion with the actual FoodViewModel used by the app
// (com.example.mdp.usda.viewmodel.FoodViewModel) and to eliminate
// unused/dead code reported by static analysis.