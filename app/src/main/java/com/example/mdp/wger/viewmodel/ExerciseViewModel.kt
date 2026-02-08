package com.example.mdp.wger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp.wger.model.BodyPart
import com.example.mdp.wger.model.Exercise
import com.example.mdp.wger.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _bodyParts = MutableStateFlow<List<BodyPart>>(emptyList())
    val bodyParts: StateFlow<List<BodyPart>> = _bodyParts

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allExercises: List<Exercise> = emptyList()

    init {
        observeSearchQuery()
    }

    fun loadBodyParts() {
        viewModelScope.launch {
            val parts = repository.fetchBodyPartList()
            _bodyParts.value = parts
        }
    }

    fun loadExercises(bodyPartName: String) {
        viewModelScope.launch {
            val exercises = repository.fetchExercisesByBodyPart(bodyPartName, _bodyParts.value)
            allExercises = exercises
            _exercises.value = exercises
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchQuery
            .debounce(500L)
            .distinctUntilChanged()
            .onEach { query ->
                val filtered = if (query.isBlank()) {
                    allExercises
                } else {
                    allExercises.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }
                _exercises.value = filtered
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }
}
