package com.example.mdp.imgur.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.imgur.model.ImgurResponse
import com.example.mdp.imgur.repository.ImgurRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ImgurViewModel(private val imgurRepository: ImgurRepository) : ViewModel() {

    fun uploadImage(image: MultipartBody.Part, onResult: (Result<ImgurResponse>) -> Unit) {
        viewModelScope.launch {
            val result = imgurRepository.uploadImage(image)
            onResult(result)
        }
    }
}
