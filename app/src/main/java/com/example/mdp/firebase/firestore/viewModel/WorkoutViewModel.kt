package com.example.mdp.firebase.firestore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.data.model.Workout
import com.example.mdp.firebase.firestore.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {

    val getallWorkouts: StateFlow<List<Workout>> = workoutRepository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    fun insertWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.insertWorkout(workout)
    }

    fun getWorkoutById(id: String): Workout? {
        return getallWorkouts.value.find { it.id == id }
    }

    //modify existing workout
    fun updateWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.insertWorkout(workout)
    }

    fun getWorkoutByDate(date: String): Workout? {
        return getallWorkouts.value.find { it.date == date }
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.deleteWorkout(workoutId = workout.id)
    }
}