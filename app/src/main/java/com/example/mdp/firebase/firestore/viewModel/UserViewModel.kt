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
                val updatedCalories = calculateDailyCalories(
                    gender = userInfo!!.gender,
                    weight = userInfo.weight,
                    height = userInfo.height,
                    age = userInfo.age
                )
                val updatedUser = userInfo.copy(dailyCalories = updatedCalories)
                _user.value = updatedUser
                Log.d("UserViewModel", "User info loaded: $updatedUser")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user: ${e.message}")
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            _user.value = user
        }
    }

    private fun calculateDailyCalories(gender: String, weight: Float, height: Float, age: Int): Float {
        val bmr = if (gender.lowercase() == "male") {
            10 * weight + 6.25f * height - 5 * age + 5
        } else {
            10 * weight + 6.25f * height - 5 * age - 161
        }

        val activityFactor = 1.2f // Sedentary, you can customize this later
        return bmr * activityFactor
    }
}


