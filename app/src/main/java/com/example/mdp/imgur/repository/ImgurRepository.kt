package com.example.mdp.imgur.repository

import com.example.mdp.imgur.ImgurApiService
import com.example.mdp.imgur.model.ImgurResponse
import okhttp3.MultipartBody

class ImgurRepository(private val apiService: ImgurApiService) {

    suspend fun uploadImage(imagePart: MultipartBody.Part): Result<ImgurResponse> {
        val accessToken = "5a64363b17c766c7652b76351d0ec105859d0801"
        return try {
            val response = apiService.uploadImage("Bearer $accessToken", imagePart)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
