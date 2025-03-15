package com.example.mdp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdp.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    init {
        // Update current user on initialization
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun register(email: String, password: String) {
        authRepository.register(email, password) { user ->
            _currentUser.postValue(user)
        }
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password) { user ->
            _currentUser.postValue(user)
        }
    }

    fun signInWithGoogle(idToken: String) {
        authRepository.firebaseAuthWithGoogle(idToken) { firebaseUser ->
            _currentUser.postValue(firebaseUser)
        }
    }

    fun logout() {
        authRepository.logout()
        _currentUser.value = null
    }


}
