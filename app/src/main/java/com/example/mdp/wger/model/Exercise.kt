package com.example.mdp.wger.model

import com.google.gson.annotations.SerializedName

data class WgerBodyPartResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<BodyPart>
)

data class BodyPart(
    val id: Int,
    val name: String
)

data class WgerExerciseResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Exercise>
)

data class Exercise(
    val id: Int,
    val name: String,
    val category: Int,
    val language: Int,
    @SerializedName("exercise_base") val exerciseBaseId: Int
)

data class WgerImageResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ExerciseImage>
)

data class ExerciseImage(
    val id: Int,
    @SerializedName("exercise_base") val exerciseBaseId: Int,
    val image: String
)