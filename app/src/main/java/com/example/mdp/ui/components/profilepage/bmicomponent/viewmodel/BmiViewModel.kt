package com.example.mdp.ui.components.profilepage.bmicomponent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class BmiViewModel : ViewModel() {
    var age by mutableStateOf(0)
    var weight by mutableStateOf(0.0)
    var height by mutableStateOf(0.0)
    var gender by mutableStateOf("male")

    var bmi by mutableStateOf(0.0)
}