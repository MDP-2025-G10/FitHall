package com.example.mdp.api

import com.example.mdp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface USDAApiService {

    @GET("foods/search")
    suspend fun searchFood(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.USDA_API_KEY
    ): FoodSearchResponse
}
