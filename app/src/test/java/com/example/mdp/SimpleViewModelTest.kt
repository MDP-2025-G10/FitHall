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
    fun testViewModelIsCreated() {
        assertNotNull(dateViewModel)
    }

    @Test
    fun testTodayIsNotNull() {
        assertNotNull(dateViewModel.today.value)
    }

    @Test
    fun testSelectedDateIsNotNull() {
        assertNotNull(dateViewModel.selectedDate.value)
    }
}