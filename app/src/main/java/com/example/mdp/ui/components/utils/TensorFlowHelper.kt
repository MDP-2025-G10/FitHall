package com.example.mdp.ui.components.utils

import android.content.Context
import coil3.Bitmap
import com.example.mdp.firebase.firestore.viewModel.MealViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

object TensorFlowHelper {
    private lateinit var tflite: Interpreter
    private lateinit var labels: List<String>
    private lateinit var interpreter: Interpreter

    fun loadModel(context: Context, modelName: String = "food_model.tflite") {
        val model = loadModelFile(context, modelName)
        tflite = Interpreter(model)

        labels = FoodRecognitionLabels.loadLabels(context)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classify(bitmap: Bitmap, mealViewModel: MealViewModel) {
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        val inputBuffer = tensorImage.buffer

        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 2024), DataType.FLOAT32)

        interpreter.run(inputBuffer, outputBuffer.buffer.rewind())

        val outputArray = outputBuffer.floatArray

        val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1

        if (maxIndex != -1) {
            mealViewModel.onPredictionResult(maxIndex)
        }
    }

    fun runInference(inputBuffer: ByteBuffer, numberOfClasses: Int = 2024): ByteArray {
        val output = Array(1) { ByteArray(numberOfClasses) }
        tflite.run(inputBuffer, output)
        return output[0]
    }

    fun getTopPrediction(output: ByteArray, labels: List<String>): Pair<String, Float> {
        val probabilities = output.map { it.toInt() and 0xFF }
            .map { it * 0.00390625f }

        val maxIndex = output.indices.maxByOrNull { probabilities[it] } ?: -1
        val label = if (maxIndex in labels.indices) labels[maxIndex] else "Unknown"
        val confidence = if (maxIndex != -1) probabilities[maxIndex] else 0f
        return label to confidence
    }
}