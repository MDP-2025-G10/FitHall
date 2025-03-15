package com.example.mdp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mdp.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        Log.d("AuthViewModel", "Auth state changed: ${firebaseAuth.currentUser?.email}")
        _currentUser.postValue(firebaseAuth.currentUser) // Update LiveData when auth state changes
    }

    init {
        // Update current user on initialization
        _currentUser.value = authRepository.getCurrentUser()
        authRepository.addAuthStateListener(authStateListener)
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
        Log.d("currentUser", "${_currentUser.value} from AuthViewModel logout")
    }

    override fun onCleared() {
        super.onCleared()
        authRepository.removeAuthStateListener(authStateListener) // Cleanup listener to prevent memory leaks
    }
}
