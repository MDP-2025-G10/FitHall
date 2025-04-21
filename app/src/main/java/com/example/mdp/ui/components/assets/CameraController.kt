package com.example.mdp.ui.components.assets

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat

object CameraController {
    var imageCapture: ImageCapture? = null

    fun captureImage(context: Context, onImageCapture: (ImageProxy) -> Unit) {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy){
                    onImageCapture(image)
                    image.close()
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.e("Capture", "Failed: ${exception.message}", exception)
                }
            }
        )
    }
}