package com.example.mdp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.data.models.Meal
import com.example.mdp.data.models.NutritionInfo
import com.example.mdp.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MealViewModel(private val mealRepository: MealRepository) : ViewModel() {

    val todayNutrition: StateFlow<NutritionInfo> = mealRepository.getTodayNutrition()
        .stateIn(viewModelScope, SharingStarted.Lazily, NutritionInfo())

    val allMeals: Flow<List<Meal>> = mealRepository.allMeals

    // Insert a meal into the database
    fun insertMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        mealRepository.insertMeal(meal)
    }

    // Delete a meal from the database
    fun deleteMeal(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        mealRepository.deleteMeal(meal)
    }

}