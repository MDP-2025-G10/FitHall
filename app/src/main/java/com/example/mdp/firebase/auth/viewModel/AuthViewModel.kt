package com.example.mdp.firebase.auth.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.firebase.auth.repository.AuthRepository
import com.example.mdp.firebase.firestore.model.User
import com.example.mdp.firebase.firestore.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _navigateToNameStep = MutableLiveData<Boolean>()
    val navigateToNameStep: LiveData<Boolean> = _navigateToNameStep

    init {
        viewModelScope.launch {
            authRepository.authStateFlow.collect { user ->
                Log.d("AuthViewModel", "Auth state changed: ${user?.email}")
                _currentUser.value = user
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.register(email, password)

            result.onSuccess { firebaseUser ->
                val newUser = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    profilePic = firebaseUser.photoUrl?.toString() ?: ""
                )
                userRepository.createUserIfNotExists(newUser)

                _navigateToNameStep.value = true
            }

            result.onFailure {
                Log.e("AuthViewModel", "Register failed: ${it.message}")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)

            result.onSuccess { firebaseUser ->
                Log.d("AuthViewModel", "Login success: ${firebaseUser.email}")
                val user = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    profilePic = firebaseUser.photoUrl?.toString() ?: ""
                )
                userRepository.createUserIfNotExists(user)
            }

            result.onFailure {
                Log.e("AuthViewModel", "Login failed: ${it.message}")
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {

            val result = authRepository.signInWithGoogle()

            result.onSuccess { firebaseUser ->

                val isNewUser =
                    firebaseUser.metadata?.creationTimestamp ==
                            firebaseUser.metadata?.lastSignInTimestamp

                val user = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    profilePic = firebaseUser.photoUrl?.toString() ?: ""
                )

                userRepository.createUserIfNotExists(user)

                if (isNewUser) {
                    _navigateToNameStep.value = true
                }
            }

            result.onFailure {
                Log.e("AuthViewModel", "Google sign-in failed: ${it.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun onNavigatedToNameStep() {
        _navigateToNameStep.value = false
    }
}
