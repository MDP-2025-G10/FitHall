package com.example.mdp.ui.components.utils

import android.content.Context

object FoodRecognitionLabels {
    fun loadLabels(context: Context, fileName: String = "foodRecognitionLabels.txt"): List<String> {
        return context.assets.open(fileName).bufferedReader().readLines()
    }
}