package com.example.mdp.imgur.model

data class ImgurResponse(
    val data: ImgurData,
    val success: Boolean,
    val status: Int
)

data class ImgurData(
    val id: String,
    val link: String
)

data class ImgurAuthResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val refresh_token: String
)
