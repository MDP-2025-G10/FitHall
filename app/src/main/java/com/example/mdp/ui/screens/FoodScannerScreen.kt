package com.example.mdp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mdp.ui.components.assets.foodRecognitionLabels
import com.example.mdp.ui.components.assets.getNutritionInfo


@Composable
fun FoodScannerScreen(navController: NavController) {
    // This is where you would implement the UI for the Food Scanner screen
    // You can use Jetpack Compose to create the UI components
    // For example, you can create a button to capture an image and display the results
    // You can also use the foodRecognitionLabels and getNutritionInfo functions here
    // to process the captured image and fetch nutrition information
    var detectedFood by remember { mutableStateOf("") }
    var nutritionInfo by remember { mutableStateOf("") }
    var showCamera by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (showCamera) {
            Camera { bitmap ->
                foodRecognitionLabels (bitmap) { foodList ->
                    if (foodList.isNotEmpty()) {
                        detectedFood = foodList[0]
                        getNutritionInfo(detectedFood) { nutrition ->
                            nutritionInfo = nutrition
                            showCamera = false
                        }
                    }
                }
            }
        }
    }
}