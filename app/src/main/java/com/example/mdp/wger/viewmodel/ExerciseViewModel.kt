package com.example.mdp.wger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.wger.model.BodyPart
import com.example.mdp.wger.model.Exercise
import com.example.mdp.wger.model.ExerciseImage
import com.example.mdp.wger.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _bodyParts = MutableStateFlow<List<BodyPart>>(emptyList())
    val bodyParts: StateFlow<List<BodyPart>> = _bodyParts

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private val _exerciseImages = MutableStateFlow<List<ExerciseImage>>(emptyList())
    val exerciseImages: StateFlow<List<ExerciseImage>> = _exerciseImages

    fun loadBodyParts() {
        viewModelScope.launch {
            val parts = repository.fetchBodyPartList()
            _bodyParts.value = parts
        }
    }

    fun loadExercises(bodyPartName: String) {
        viewModelScope.launch {
            val exercises = repository.fetchExercises(bodyPartName, _bodyParts.value)
            _exercises.value = exercises
        }
    }

    fun loadExerciseImages(exerciseId: Int) {
        viewModelScope.launch {
            val images = repository.fetchExerciseImages(exerciseId, _exercises.value)
            _exerciseImages.value = images
        }
    }
}
