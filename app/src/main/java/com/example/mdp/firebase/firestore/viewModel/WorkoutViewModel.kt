package com.example.mdp.firebase.firestore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.firebase.firestore.model.Workout
import com.example.mdp.firebase.firestore.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import com.example.mdp.firebase.firestore.model.Set

class WorkoutViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {

    private val _weightInput = MutableStateFlow("")
    val weightInput = _weightInput.asStateFlow()

    private val _repsInput = MutableStateFlow("")
    val repsInput = _repsInput.asStateFlow()

    private val _sets = MutableStateFlow<List<Set>>(emptyList())
    val sets: StateFlow<List<Set>> = _sets

    fun setWeightInput(value: String) {
        _weightInput.value = value
    }

    fun setRepsInput(value: String) {
        _repsInput.value = value
    }

    fun addSetToWorkout(exerciseName: String) {
        val reps = _repsInput.value.toIntOrNull() ?: return
        val weight = _weightInput.value.toIntOrNull() ?: return
        val newSet = Set(reps = reps, weight = weight)
        _sets.value += newSet
        _weightInput.value = ""
        _repsInput.value = ""
    }

    fun clearSetInputs() {
        _weightInput.value = ""
        _repsInput.value = ""
    }

    fun clearWorkoutState() {
        _sets.value = emptyList()
        _weightInput.value = ""
        _repsInput.value = ""
    }


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