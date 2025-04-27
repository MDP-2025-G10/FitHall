package com.example.mdp.ui.screens

import android.content.Context
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mdp.firebase.firestore.viewModel.MealViewModel
import com.example.mdp.ui.components.utils.getNutritionInfo
import com.example.mdp.ui.components.utils.FoodRecognitionLabels
import com.example.mdp.ui.components.utils.TensorFlowHelper
import com.example.mdp.ui.components.utils.convertBitmapToByteBuffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun FoodScannerScreen(navController: NavController, context: Context, mealViewModel: MealViewModel, cameraController: CameraController) {
    var detectedFood by remember { mutableStateOf("") }
    var nutritionData by remember { mutableStateOf("") }
    var showCamera by remember { mutableStateOf(true) }
    val labels = remember { FoodRecognitionLabels.loadLabels(context) }
    val nutrition by mealViewModel.nutrition.collectAsState()
    val predictedLabel by mealViewModel.predictedLabel.collectAsState()


    // Load the TensorFlow model
    LaunchedEffect(Unit) {
        TensorFlowHelper.loadModel(context)
        mealViewModel.init(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showCamera) {
            Camera(
                onImageCapture = { bitmap ->
                    TensorFlowHelper.classify(bitmap, mealViewModel)
                // Convert the bitmap to ByteBuffer
                val byteBuffer = convertBitmapToByteBuffer(bitmap)
                // Run inference
                val output = TensorFlowHelper.runInference(inputBuffer = byteBuffer)
                val (predictedLabel, confidence) = TensorFlowHelper.getTopPrediction(output, labels)

                // Fetch nutrition info
                CoroutineScope(Dispatchers.Main).launch {
                    val nutritionalData = getNutritionInfo(predictedLabel)

                    withContext(Dispatchers.Main) {
                        if (nutritionalData != null) {
                            detectedFood = predictedLabel
                            nutritionData =
                                "Calories: ${nutritionalData.calories}, Protein: ${nutritionalData.protein}g, Carbs: ${nutritionalData.carbs}g, Fat: ${nutritionalData.fat}g"
                            showCamera = false

                            // Save the prediction to Firestore
                            mealViewModel.savePrediction(
                                label = predictedLabel,
                                calories = nutritionalData.calories,
                                fat = nutritionalData.fat,
                                carbs = nutritionalData.carbs,
                                protein = nutritionalData.protein,
                                confidence = confidence
                            )
                        } else {
                            detectedFood = "Unknown food item"
                            nutritionData = "No nutritional information available"
                        }
                    }
                }
            },
                cameraController = cameraController,
                mealViewModel = mealViewModel
            )
        } else {
            if (nutrition != null) {
                NutritionCard(nutrition = nutrition!!)
            } else {
                Text(
                    text = "Take a picture of your food",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}