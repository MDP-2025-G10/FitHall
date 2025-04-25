package com.example.mdp.wger.repository

import android.util.Log
import androidx.core.text.HtmlCompat
import com.example.mdp.wger.WgerApiService
import com.example.mdp.wger.model.BodyPart
import com.example.mdp.wger.model.Exercise
import com.example.mdp.wger.model.ExerciseApiModel

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
                Log.d("ExerciseRepository", "Fetched exercises: $response")
                response.results.mapNotNull { mapToExercise(it) }
            }
        } catch (e: Exception) {
            Log.e("ExerciseRepository", "API Call Failed: ${e.message}", e)
            emptyList()
        }
    }

    private fun mapToExercise(exerciseApi: ExerciseApiModel): Exercise? {
        val translation = exerciseApi.translations.find { it.language == 2 }
        return translation?.let {
            val plainText = HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            Exercise(
                id = exerciseApi.id,
                name = it.name,
                category = exerciseApi.category,
                description = plainText,
                imageUrl = exerciseApi.images.firstOrNull()?.image ?: "",
            )
        }
    }

}
