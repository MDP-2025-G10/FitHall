package com.example.mdp.imgur

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImgurRetrofitInstance {
    private const val BASE_URL = "https://api.imgur.com/3/"

    val api: ImgurApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgurApiService::class.java)
    }
}
