package com.example.mdp.data.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class DateViewModel : ViewModel() {

    private val _today = mutableStateOf(LocalDate.now())

    private val _selectedDate = mutableStateOf(LocalDate.now())

    val today: State<LocalDate> = _today
    val selectedDate: State<LocalDate> = _selectedDate

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        Log.d("DateViewModel","today: ${today.value}")
        Log.d("DateViewModel","selectedDate: ${selectedDate.value}")
    }
}