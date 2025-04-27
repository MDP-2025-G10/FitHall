package com.example.mdp.ui.screens

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
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
import androidx.compose.runtime.rememberCoroutineScope
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
fun FoodScannerScreen(navController: NavController, context: Context, mealViewModel: MealViewModel) {
    var detectedFood by remember { mutableStateOf("") }
    var nutritionData by remember { mutableStateOf("") }
    var showCamera by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }

    val labels = remember { FoodRecognitionLabels.loadLabels(context) }
    val nutrition by mealViewModel.nutrition.collectAsState()

    // Load the TensorFlow model
    LaunchedEffect(Unit) {
        TensorFlowHelper.loadModel(context)
        mealViewModel.init(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showCamera) {
            Camera(
                onImageCapture = { bitmap ->
                    isLoading = true // Show loading when image captured

                        // Run classification
//                        TensorFlowHelper.classify(bitmap, mealViewModel)

                        val byteBuffer = convertBitmapToByteBuffer(bitmap)
                        val output = TensorFlowHelper.runInference(byteBuffer)
                        val (predicted, confidence) = TensorFlowHelper.getTopPrediction(output, labels)

                    CoroutineScope(Dispatchers.Main).launch {
                        val nutritionalData = getNutritionInfo(predicted)

                        withContext(Dispatchers.Main) {
                        if (nutritionalData != null) {
                            detectedFood = predicted
                            nutritionData = "Calories: ${nutritionalData.calories}, Protein: ${nutritionalData.protein}g, Carbs: ${nutritionalData.carbs}g, Fat: ${nutritionalData.fat}g"
                            showCamera = false

                            mealViewModel.savePrediction(
                                label = predicted,
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
                           isLoading = false // Hide loading after done
                        }
                    }
                },
                isLoading = isLoading,
//                cameraController = cameraController
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