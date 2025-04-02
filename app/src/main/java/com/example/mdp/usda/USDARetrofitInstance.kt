package com.example.mdp.usda

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object USDARetrofitInstance {
    private const val BASE_URL = "https://api.nal.usda.gov/fdc/v1/"

    val api: USDAApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(USDAApiService::class.java)
    }
}
