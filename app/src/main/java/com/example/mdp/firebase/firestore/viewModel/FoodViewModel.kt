package com.example.mdp.firebase.firestore.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.mdp.ui.components.utils.FoodRecognitionLabels
import com.example.mdp.ui.components.utils.NutritionUtils

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val _nutrition = mutableStateOf<Nutrition?>(null)
    val nutrition: State<Nutrition?> = _nutrition

    private lateinit var labels: List<String>
    private lateinit var nutritionList: List<Nutrition>

    fun initializeResources(context: Context) {
        labels = FoodRecognitionLabels.loadLabels(context)
        nutritionList = NutritionUtils.loadNutritionData(context)
    }

    fun onPredictionResult(index: Int) {
        val label = labels.getOrNull(index)
        label?.let {
            _nutrition.value = NutritionUtils.findNutrition(it, nutritionList)
        }
    }
}