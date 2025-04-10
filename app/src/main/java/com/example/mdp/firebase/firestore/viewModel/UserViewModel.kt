package com.example.mdp.firebase.firestore.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.firebase.firestore.model.User
import com.example.mdp.firebase.firestore.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    init {
        Log.d("UserViewModel", "UserViewModel created with repository: $repository")
    }

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun loadUser(uid: String) {
        viewModelScope.launch {
            try {
                val userInfo = repository.getUser(uid)
                _user.value = userInfo
                Log.d("UserViewModel", "User info loaded: $userInfo")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user: ${e.message}")
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
//            _user.value = user
        }
    }
}
