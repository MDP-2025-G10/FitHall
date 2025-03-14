package com.example.mdp.ui.viewmodels

import android.credentials.CredentialManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser


    init {
        // Update current user on initialization
        _currentUser.value = auth.currentUser
    }


    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _currentUser.value = auth.currentUser
                Log.d("AuthViewModel", "Registration successful: ${auth.currentUser?.email}")
            } else {
                _currentUser.value = null
                Log.d("AuthViewModel", "Registration fail:", task.exception)
            }
        }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _currentUser.value = auth.currentUser
                Log.d("AuthViewModel", "Login successful: ${auth.currentUser?.email}")
            } else {
                _currentUser.value = null
                Log.e("AuthViewModel", "Login failed", task.exception)
            }
        }
    }

    fun logout() {
        auth.signOut()
        _currentUser.value = null
    }

}
