package com.example.mdp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.liveData
import com.example.mdp.data.database.WorkoutDatabase
import com.example.mdp.data.models.Workouts
import com.example.mdp.data.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutDao = WorkoutDatabase.getDatabase(application).workoutDao()
    private val workoutRepository = WorkoutRepository(workoutDao)

    // LiveData to observe meal data
    val allWorkouts: LiveData<List<Workouts>> = liveData(Dispatchers.IO) {
        emit(workoutRepository.getAllWorkouts())
    }

    // Insert a meal into the database
    fun insertWorkout(workout: Workouts) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutRepository.insertWorkout(workout)
        }
    }

    // Delete a meal from the database
    fun deleteWorkout(workout: Workouts) {
        viewModelScope.launch(Dispatchers.IO) {
           workoutRepository.deleteWorkout(workout)
        }
    }
    fun insertTestWorkout() = viewModelScope.launch {
       workoutRepository.insertTestWorkout()
    }
}