package com.example.mdp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mdp.ui.components.utils.foodRecognitionLabels
import com.example.mdp.ui.components.assets.getNutritionInfo


@Composable
fun FoodScannerScreen(navController: NavController, context: Context) {
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
            Camera(onImageCapture = { bitmap ->
                foodRecognitionLabels (context, bitmap) { foodList ->
                    if (foodList.isNotEmpty()) {
                        detectedFood = foodList[0]
                        getNutritionInfo(detectedFood) { nutrition ->
                            nutritionInfo = nutrition
                            showCamera = false
                        }
                    }
                }
            })
        } else {
            Text("Detected Food: $detectedFood", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(nutritionInfo, fontSize = 18.sp)
            // Add a button to go back to the camera
            Button(onClick = { showCamera = true }) {
                Text("Scan Again")
            }
        }
    }
}