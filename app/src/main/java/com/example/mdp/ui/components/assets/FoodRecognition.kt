@file:Suppress("DEPRECATION")

package com.example.mdp.ui.components.assets

import android.graphics.Bitmap
import android.util.Log
import android.content.Context
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerLocalModel
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions
import java.nio.ByteBuffer

fun foodRecognitionLabels(context: Context, bitmap: Bitmap, onResult: (List<String>) -> Unit) {

    val image = InputImage.fromBitmap(bitmap, 0)
    val localModel = AutoMLImageLabelerLocalModel.Builder()
        .setAssetFilePath("./food_model.tflite")
        .build()
    val options = AutoMLImageLabelerOptions.Builder(localModel).build()
    val labeler = ImageLabeling.getClient(options)

    labeler.process(image)
        .addOnSuccessListener { labels ->
            val foodItems = labels.map { it.text }
            onResult(foodItems)
        }
        .addOnFailureListener { exception ->
            Log.e("MLKit", "Food Recognition Failed: ${exception.message}")
        }
}