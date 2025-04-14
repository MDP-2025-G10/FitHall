package com.example.mdp.firebase.firestore.model


data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val profilePic: String = "",
    val birthday: String = "",
    val height: Float = 0f,
    val weight: Float = 0f,
    val age: Int = 0,
    val gender: String = "",
    val dailyCalories: Float = 0f
)

