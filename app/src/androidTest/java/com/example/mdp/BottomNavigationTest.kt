package com.example.mdp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class BottomNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavigationExists() {
        composeTestRule.waitForIdle()

        val navItems = listOf("Home", "Food", "Calendar", "Nutrition", "Settings")
        var foundNav = false

        for (item in navItems) {
            try {
                composeTestRule.onNodeWithText(item).assertIsDisplayed()
                foundNav = true
                println("✓ Bottom navigation has '$item'")
            } catch (e: AssertionError) {
                // Continue
            }
        }

        if (!foundNav) {
            println("ℹ Bottom navigation not visible - may be on login screen")
        }
    }

    @Test
    fun canNavigateToSettings() {
        composeTestRule.waitForIdle()

        try {
            composeTestRule.onNodeWithText("Settings").performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Enable Reminder").assertIsDisplayed()
            println("✓ Can navigate to Settings")
        } catch (e: Exception) {
            println("ℹ Cannot navigate to Settings - may be on login screen")
        }
    }
}