package com.example.mdp.data.database

import androidx.room.*
import com.example.mdp.data.models.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Query("SELECT * FROM meals ORDER BY timestamp DESC")
    suspend fun getAllMeals(): List<Meal>

    @Delete
    suspend fun deleteMeal(meal: Meal)
}