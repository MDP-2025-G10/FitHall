package com.example.mdp.imgur

import com.example.mdp.imgur.model.ImgurAuthResponse
import com.example.mdp.imgur.model.ImgurResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApiService {
    @Multipart
    @POST("image")
    suspend fun uploadImage(
        @Header("Authorization") authHeader: String,
        @Part image: MultipartBody.Part,
    ): ImgurResponse


    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun refreshAccessToken(
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String = "refresh_token"
    ): ImgurAuthResponse
}
