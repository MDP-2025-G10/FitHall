package com.example.mdp.usda

class FoodRepository(private val apiService: USDAApiService) {

    suspend fun searchFood(query: String): FoodSearchResponse {
        return apiService.searchFood(query)
    }
}
