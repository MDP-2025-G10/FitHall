package com.example.mdp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate
import com.example.mdp.firebase.firestore.viewModel.DateViewModel

class SimpleViewModelTest {

    private lateinit var dateViewModel: DateViewModel

    @Before
    fun setUp() {
        dateViewModel = DateViewModel()
    }

    @Test
    fun testTodayIsCurrentDate() {
        val result = dateViewModel.today.value
        assertEquals(LocalDate.now(), result)
    }

    @Test
    fun testSelectedDateStartsAsToday() {
        val result = dateViewModel.selectedDate.value
        assertEquals(LocalDate.now(), result)
    }

    @Test
    fun testCanChangeSelectedDate() {
        val tomorrow = LocalDate.now().plusDays(1)
        dateViewModel.setSelectedDate(tomorrow)

        val result = dateViewModel.selectedDate.value
        assertEquals(tomorrow, result)
    }

    @Test
    fun testTodayNeverChanges() {
        val originalToday = dateViewModel.today.value

        val tomorrow = LocalDate.now().plusDays(1)
        dateViewModel.setSelectedDate(tomorrow)

        val result = dateViewModel.today.value
        assertEquals(originalToday, result)
    }
}