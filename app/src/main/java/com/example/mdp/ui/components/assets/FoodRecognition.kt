@file:Suppress("DEPRECATION")

package com.example.mdp.ui.components.assets

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions

fun foodRecognitionLabels(bitmap: Bitmap, onResult: (List<String>) -> Unit) {
    val image = InputImage.fromBitmap(bitmap, 0)
    val labeler = ImageLabeling.getClient(AutoMLImageLabelerOptions.DEFAULT_OPTIONS)

    labeler.process(image)
        .addOnSuccessListener { labels ->
            val foodItems = labels.map { it.text }
            onResult(foodItems)
        }
        .addOnFailureListener { exception ->
            Log.e("MLKit", "Food Recognition Failed: ${exception.message}")
        }
}