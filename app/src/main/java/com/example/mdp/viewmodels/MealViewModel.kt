package com.example.mdp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.liveData
import com.example.mdp.data.database.MealDatabase
import com.example.mdp.data.models.Meal
import com.example.mdp.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(application: Application) : AndroidViewModel(application) {

    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val mealRepository = MealRepository(mealDao)

    // LiveData to observe meal data
    val allMeals: LiveData<List<Meal>> = liveData(Dispatchers.IO) {
        emit(mealRepository.getAllMeals())
    }

    // Insert a meal into the database
    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.insertMeal(meal)
        }
    }

    // Delete a meal from the database
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.deleteMeal(meal)
            }
        }
    fun insertTestMeal() = viewModelScope.launch {
        mealRepository.insertTestMeal()
    }
}