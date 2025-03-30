@file:Suppress("UNCHECKED_CAST")

package com.example.mdp.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.Executors


@Composable
fun Camera(onImageCaptured: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageCapture = ImageCapture.Builder().build()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )

                // Capture image when button is clicked
                previewView.setOnClickListener {
                    val outputFileOptions = ImageCapture.OutputFileOptions
                        .Builder(File(ctx.externalCacheDir, "food_image.jpg"))
                        .build()

                    imageCapture.takePicture(outputFileOptions, Executors.newSingleThreadExecutor(),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val bitmap = BitmapFactory.decodeFile(outputFileResults.savedUri?.path)
                                onImageCaptured(bitmap)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("Camera", "Image Capture Failed: ${exception.message}")
                            }
                        })
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}