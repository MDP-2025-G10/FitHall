package com.example.mdp.firebase.firestore.viewModel

data class Nutrition(
    val label: String,
    val calories: Int,
    val protein: Int,
    val carbohydrates: Int,
    val fats: Int,
    val fiber: Int,
    val sugars: Int,
    val sodium: Int
)