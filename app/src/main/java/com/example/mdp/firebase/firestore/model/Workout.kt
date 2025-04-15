package com.example.mdp.firebase.firestore.model

data class WorkoutSet(
    val reps: Int = 0,
    val weight: Int = 0
)

data class Exercise(
    val name: String = "",
    val sets: List<WorkoutSet> = emptyList()
)

data class Workout(
    val id: String = "",
    val date: String = "", // You can also use Timestamp
    val name: String = "",
    val description: String = "",
    val exercises: List<Exercise> = emptyList()
)