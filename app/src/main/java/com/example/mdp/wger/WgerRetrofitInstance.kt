package com.example.mdp.wger

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WgerRetrofitInstance {
    private const val BASE_URL = "https://wger.de/api/v2/"

    val api: WgerApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WgerApiService::class.java)
    }
}
