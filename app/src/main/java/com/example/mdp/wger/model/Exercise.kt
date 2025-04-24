package com.example.mdp.wger.model


import com.google.gson.annotations.SerializedName

data class WgerBodyPartResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<BodyPart>
)

data class BodyPart(
    val id: Int = 0,
    val name: String = ""
)

data class WgerExerciseResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ExerciseApiModel>
)

data class ExerciseApiModel(
    val id: Int,
    val category: BodyPart,
    val translations: List<Translation>,
    val images: List<ExerciseImage>
)

data class Translation(
    val id: Int,
    val name: String,
    val language: Int,
    val description: String
)

data class ExerciseImage(
    val id: Int,
    @SerializedName("exercise") val exerciseId: Int,
    val image: String
)

data class Exercise(
    val id: Int = 0,
    val name: String = "",
    val category: BodyPart = BodyPart(),
    val description: String = "",
    val imageUrl: String = "",
)




//data class WgerImageResponse(
//    val count: Int,
//    val next: String?,
//    val previous: String?,
//    val results: List<ExerciseImage>
//)

