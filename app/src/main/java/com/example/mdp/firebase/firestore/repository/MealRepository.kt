package com.example.mdp.firebase.firestore.repository

import android.util.Log
import com.example.mdp.firebase.firestore.model.DailyCalories
import com.example.mdp.firebase.firestore.model.Meal
import com.example.mdp.firebase.firestore.model.NutritionInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale.getDefault


class MealRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun userMealsCollection() =
        db.collection("users")
            .document(auth.currentUser?.uid ?: "default")
            .collection("meals")

    suspend fun insertMeal(meal: Meal) {
        try {
            val mealRef = userMealsCollection().document()
            mealRef.set(meal.copy(id = mealRef.id)).await()
        } catch (e: Exception) {
            Log.e("MealRepository", "Error inserting meal to firestore", e)
        }
    }

    fun getAllMeals(): Flow<List<Meal>> = callbackFlow {
        val listener = userMealsCollection().addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }
            val meals =
                snapshot?.documents?.mapNotNull { it.toObject(Meal::class.java) } ?: emptyList()
            trySend(meals).isSuccess
        }
        awaitClose { listener.remove() }
    }

    fun getMealsForDate(date: LocalDate): Flow<List<Meal>> = callbackFlow {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond() * 1000
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond() * 1000

        val listener = userMealsCollection()
            .whereGreaterThan("timestamp", startOfDay)
            .whereLessThan("timestamp", endOfDay)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                val meals =
                    snapshot?.documents?.mapNotNull { it.toObject(Meal::class.java) } ?: emptyList()
                trySend(meals).isSuccess
            }
        awaitClose { listener.remove() }
    }


    fun getTodayNutrition(): Flow<NutritionInfo> = getAllMeals().map { meals ->
        val now = System.currentTimeMillis()
        val midnight = now - (now % (24 * 60 * 60 * 1000))
        val todayMeals = meals.filter { it.timestamp in midnight..now }

        NutritionInfo(
            calories = todayMeals.sumOf { it.calories },
            fats = todayMeals.sumOf { it.fats },
            carbs = todayMeals.sumOf { it.carbs },
            proteins = todayMeals.sumOf { it.proteins }
        )
    }

    fun getCaloriesForLast7Days(): Flow<List<DailyCalories>> = getAllMeals().map { meals ->
        val now = System.currentTimeMillis()
        val sevenDaysAgo = now - (7 * 24 * 60 * 60 * 1000)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", getDefault())

        val recentMeals = meals.filter { it.timestamp in sevenDaysAgo..now }

        Log.d("MealRepository", "Meals from last 7 days: $recentMeals")

        recentMeals.groupBy { dateFormat.format(Date(it.timestamp)) }
            .map { (date, dailyMeals) ->
                DailyCalories(
                    totalCalories = dailyMeals.sumOf { it.calories },
                    date = date
                )
            }.sortedBy { it.date }
    }

    // Delete a meal from the database
    suspend fun deleteMeal(meal: Meal) {
        try {
            userMealsCollection().document(meal.id).delete().await()
        } catch (e: Exception) {
            Log.e("MealRepository", "Error deleting meal from Firestore", e)
        }
    }
}


