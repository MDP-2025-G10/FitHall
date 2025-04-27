package com.example.mdp.ui.components.utils

import android.content.Context
import com.example.mdp.firebase.firestore.viewModel.Nutrition
import java.io.BufferedReader
import java.io.InputStreamReader

object NutritionUtils {

    fun loadNutritionData(context: Context, fileName: String = "nutrition_100g.csv"): List<Nutrition> {
        val nutritionList = mutableListOf<Nutrition>()

        val reader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
        reader.readLine() // Skip header

        reader.forEachLine { line ->
            val parts = line.split(",")

            if (parts.size >= 8) {
                try {
                    nutritionList.add(
                        Nutrition(
                            label = parts[0].trim(),
                            calories = parts[2].toInt(),
                            protein = parts[3].toInt(),
                            carbohydrates = parts[4].toInt(),
                            fats = parts[5].toInt(),
                            fiber = parts[6].toInt(),
                            sugars = parts[7].toInt(),
                            sodium = parts[8].toInt()
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return nutritionList
    }

    fun findNutrition(label: String, nutritionList: List<Nutrition>): Nutrition? {
        return nutritionList.find {
            it.label.equals(label, ignoreCase = true)
        }
    }
}