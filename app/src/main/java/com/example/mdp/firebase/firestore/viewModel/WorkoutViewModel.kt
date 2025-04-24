package com.example.mdp.firebase.firestore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.firebase.firestore.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {

    val allWorkoutList: StateFlow<List<Workout>> = workoutRepository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getWorkoutsForDate(date: LocalDate): StateFlow<List<Workout>> {
        return workoutRepository.getWorkoutsForDate(date)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun insertWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.insertWorkout(workout)
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.deleteWorkout(workout)
    }
}