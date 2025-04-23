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
    val results: List<ExerciseApiModel>
)

data class ExerciseApiModel(
    val id: Int,
    val uuid: String,
    val category: BodyPart,
    val translations: List<Translation>,
    val images: List<ExerciseImage>
)

data class Translation(
    val id: Int,
    val name: String,
    val language: Int
)

data class ExerciseImage(
    val id: Int,
    @SerializedName("exercise") val exerciseId: Int,
    val image: String
)

data class Exercise(
    val id: Int,
    val uuid: String,
    val name: String,
    val category: BodyPart,
    val imageUrl: String? = null
)


//data class WgerImageResponse(
//    val count: Int,
//    val next: String?,
//    val previous: String?,
//    val results: List<ExerciseImage>
//)

