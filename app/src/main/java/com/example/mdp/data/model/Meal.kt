package com.example.mdp.data.model

data class Meal(
    val id: String = "",
    val name: String = "",
    val calories: Int = 0,
    val fats: Int = 0,
    val carbs: Int = 0,
    val proteins: Int = 0,
    val imagePath: String = "",
    val timestamp: Long = System.currentTimeMillis()
)


data class NutritionInfo(
    val calories: Int = 0,
    val fats: Int = 0,
    val carbs: Int = 0,
    val proteins: Int = 0
)

data class DailyCalories(
    val totalCalories: Int,
    val date: String?, // Format: YYYY-MM-DD
)