package com.example.mdp.ui.components.utils

import android.content.Context
import com.example.mdp.firebase.firestore.viewModel.Nutrition

object NutritionUtils {

    fun loadNutritionData(context: Context, fileName: String = "nutrition_100g.csv"): List<Nutrition> {
        val nutritionList = mutableListOf<Nutrition>()

        context.assets.open(fileName).bufferedReader().use { reader ->
            reader.readLine() // Skip header

            reader.forEachLine { line ->
                val parts = line.split(",")

                if (parts.size >= 9) {
                    try {
                        nutritionList.add(
                            Nutrition(

                                label = parts[0].trim().removeSurrounding("\""),
                                calories = parts[2].trim().removeSurrounding("\"").toInt(),
                                protein = parts[3].trim().removeSurrounding("\"").toInt(),
                                carbohydrates = parts[4].trim().removeSurrounding("\"").toInt(),
                                fats = parts[5].trim().removeSurrounding("\"").toInt(),
                                fiber = parts[6].trim().removeSurrounding("\"").toInt(),
                                sugars = parts[7].trim().removeSurrounding("\"").toInt(),
                                sodium = parts[8].trim().removeSurrounding("\"").toInt()

                                label = parts[0].trim().trim('"'),
                                calories = parts[2].trim().trim('"').toInt(),
                                protein = parts[3].trim().trim('"').toInt(),
                                carbohydrates = parts[4].trim().trim('"').toInt(),
                                fats = parts[5].trim().trim('"').toInt(),
                                fiber = parts[6].trim().trim('"').toInt(),
                                sugars = parts[7].trim().trim('"').toInt(),
                                sodium = parts[8].trim().trim('"').toInt()

                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
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