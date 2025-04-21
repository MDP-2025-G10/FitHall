package com.example.mdp.ui.components.utils

import android.content.Context
import org.junit.internal.Classes
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

object TensorFlowHelper {
    private lateinit var tflite: Interpreter

    fun loadModel(context: Context, modelName: String = "food_model.tflite") {
        val model = loadModelFile(context, modelName)
        tflite = Interpreter(model)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runInference(inputBuffer: ByteBuffer, numberOfClasses: Int = 10): FloatArray {
        val output = Array(1) { FloatArray(numberOfClasses) }
        tflite.run(inputBuffer, output)
        return output[0]
    }

    fun getTopPrediction(output: FloatArray, labels: List<String>): Pair<String, Float> {
        val maxIndex = output.indices.maxByOrNull { output[it] } ?: -1
        val label = if (maxIndex in labels.indices) labels[maxIndex] else "Unknown"
        val confidence = if (maxIndex != -1) output[maxIndex] else 0f
        return label to confidence
    }
}