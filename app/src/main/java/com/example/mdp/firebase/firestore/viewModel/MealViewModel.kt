package com.example.mdp.firebase.firestore.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Index
import com.example.mdp.data.model.DailyCalories
import com.example.mdp.data.model.Meal
import com.example.mdp.data.model.NutritionInfo
import com.example.mdp.firebase.firestore.repository.MealRepository
import com.example.mdp.ui.components.utils.FoodRecognitionLabels
import com.example.mdp.ui.components.utils.NutritionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealViewModel(private val mealRepository: MealRepository) : ViewModel() {

    val todayNutrition: StateFlow<NutritionInfo> = mealRepository.getTodayNutrition()
        .stateIn(viewModelScope, SharingStarted.Lazily, NutritionInfo())

    val allMealList: StateFlow<List<Meal>> = mealRepository.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val calorieHistory: StateFlow<List<DailyCalories>> =
        mealRepository.getCaloriesForLast7Days()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _predictedLabel = MutableStateFlow<String?>(null)
    val predictedLabel: StateFlow<String?> = _predictedLabel

    private val _nutrition = MutableStateFlow<Nutrition?>(null)
    val nutrition: StateFlow<Nutrition?> = _nutrition

    private lateinit var labels: List<String>
    lateinit var nutritionList: List<Nutrition>

    fun init(context: Context) {
        labels = FoodRecognitionLabels.loadLabels(context)
        nutritionList = NutritionUtils.loadNutritionData(context)
    }

    fun getMealsForDate(date: LocalDate): StateFlow<List<Meal>> {
        return mealRepository.getMealsForDate(date)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun insertMeal(meal: Meal, imagePath: String) = viewModelScope.launch(Dispatchers.IO) {
        mealRepository.insertMeal(meal.copy(imagePath = imagePath))
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        mealRepository.deleteMeal(meal)
    }

    fun savePrediction(
        label: String,
        calories: Int,
        fat: Float,
        carbs: Float,
        protein: Float,
        confidence: Float
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.uploadMealPrediction(
                label = label,
                calories = calories,
                fat = fat,
                carbs = carbs,
                protein = protein,
                confidence = confidence,
                onSuccess = { Log.d("MealViewModel", "Prediction saved successfully") },
                onFailure = { e -> Log.e("MealViewModel", "Error saving prediction", e) }
            )
        }
    }

    fun onPredictionResult(outputIndex: Int) {
        val label = labels.getOrNull(outputIndex)
        _predictedLabel.value = label

        label?.let {
            _nutrition.value = NutritionUtils.findNutrition(it, nutritionList)
        }
    }

    fun resetPrediction() {
        _predictedLabel.value = null
        _nutrition.value = null
    }
}