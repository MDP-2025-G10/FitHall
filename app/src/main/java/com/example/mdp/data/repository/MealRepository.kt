package com.example.mdp.data.repository

import android.util.Log
import com.example.mdp.data.database.MealDao
import com.example.mdp.data.models.Meal

class MealRepository(private val mealDao: MealDao) {

    // Insert a meal into the database
    suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal)
    }

    // Get all meals from the database
    suspend fun getAllMeals(): List<Meal> {
        return mealDao.getAllMeals()
    }

    // Delete a meal from the database
    suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    // Insert a test meal into the database
    suspend fun insertTestMeal() {
       Log.d("MealRepository", "Inserting test meal")
        val testMeal = Meal(
            name = "Test Meal",
            calories = 500,
            fats = 20,
            carbs = 50,
            proteins = 30,
            imagePath = "path/to/image",
            timestamp = System.currentTimeMillis()
        )
        insertMeal(testMeal)
    }
}