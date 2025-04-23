package com.example.mdp.wger.repository

import android.util.Log
import com.example.mdp.wger.WgerApiService
import com.example.mdp.wger.model.BodyPart
import com.example.mdp.wger.model.Exercise
import com.example.mdp.wger.model.ExerciseImage

class ExerciseRepository(private val api: WgerApiService) {

    suspend fun fetchBodyPartList(): List<BodyPart> {
        return try {
            val response = api.getBodyParts()
            Log.d("ExerciseRepository", "Fetched body parts: ${response.results.size}")
            response.results
        } catch (e: Exception) {
            Log.e("ExerciseRepository", "API Call Failed: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun fetchExercisesByBodyPart(
        bodyPart: String,
        bodyParts: List<BodyPart>
    ): List<Exercise> {
        return try {
            val categoryId = bodyParts.find { it.name.equals(bodyPart, ignoreCase = true) }?.id
            if (categoryId == null) {
                Log.e("ExerciseRepository", "Body part not found for: $bodyPart")
                emptyList()
            } else {
                val response = api.getExercises(categoryId)
                Log.d("ExerciseRepository", "Fetched exercises: ${response.count}")
                response.results.mapNotNull { exerciseApi ->
                    val translation = exerciseApi.translations.find { it.language == 2 }
                    translation?.let {
                        Exercise(
                            id = exerciseApi.id,
                            uuid = exerciseApi.uuid,
                            name = it.name,
                            category = exerciseApi.category,
                            imageUrl = exerciseApi.images.firstOrNull()?.image
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ExerciseRepository", "API Call Failed: ${e.message}", e)
            emptyList()
        }
    }





//
//    suspend fun fetchExerciseImages(
//        exerciseId: Int,
//        exercises: List<Exercise>
//    ): List<ExerciseImage> {
//        return try {
//            val exerciseBaseId = exercises.find { it.id == exerciseId }?.id
//            if (exerciseBaseId == null) {
//                Log.e("ExerciseRepository", "Exercise base not found for exerciseId: $exerciseId")
//                emptyList()
//            } else {
//                val response = api.getExerciseImages(exerciseBaseId)
//                Log.d("ExerciseRepository", "Fetched images: ${response.results.size}")
//                response.results
//            }
//        } catch (e: Exception) {
//            Log.e("ExerciseRepository", "API Call Failed: ${e.message}", e)
//            emptyList()
//        }
//    }
}
