package com.example.mdp.wger

import com.example.mdp.wger.model.WgerBodyPartResponse
import com.example.mdp.wger.model.WgerExerciseResponse
import retrofit2.http.GET
import retrofit2.http.Query


// Ensures a singleton instance for efficient API interaction, like a configuration for fetching API.
interface WgerApiService {

    @GET("exerciseinfo/")
    suspend fun getExercises(
        @Query("category") categoryId: Int,
        @Query("limit") limit: Int = 600
    ): WgerExerciseResponse

    @GET("exercisecategory")
    suspend fun getBodyParts(): WgerBodyPartResponse

}