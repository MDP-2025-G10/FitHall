package com.example.mdp.firebase.firestore.model


data class Workout(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imagePath: String = "",
    val sets: List<Set> = emptyList(),
    val timestamp: Long = 0L,
)

data class Set(
    val reps: Int = 0,
    val weight: Int = 0
)