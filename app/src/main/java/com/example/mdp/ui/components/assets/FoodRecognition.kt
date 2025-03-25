@file:Suppress("DEPRECATION")

package com.example.mdp.ui.components.assets

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions

fun loadAutoMLModel(context: Context): FirebaseVisionImageLabeler {
    val model = FirebaseAutoMLLocalModel.Builder()
        .setAssetFilePath("model.tflite")
        .build()

    val options = FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(model)
        .setConfidenceThreshold(0.7f)
        .build()

    return FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)
}

fun analyzeImage(context: Context, imageUri: Uri) {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
    val image = FirebaseVisionImage.fromBitmap(bitmap)
    val labeler = loadAutoMLModel(context)

    labeler.processImage(image)
        .addOnSuccessListener { labels ->
            for (label in labels) {
                Log.d("FoodRecognition", "Label: ${label.text}, Confidence: ${label.confidence}")
            }
        }
        .addOnFailureListener { e ->
            Log.e("FoodRecognition", "Error processing image: ${e.message}")
        }
}