@file:Suppress("UNCHECKED_CAST")

package com.example.mdp.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mdp.firebase.firestore.viewModel.MealViewModel
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Composable
fun Camera(
    onImageCapture: (Bitmap) -> Unit,
    isLoading: Boolean
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var imageCapture = remember { mutableStateOf<ImageCapture?>(null) }

    // Check and request camera permissions
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CAMERA),
                0
            )
        }
    }

        Box(modifier = Modifier.fillMaxSize()) {

            // Camera preview
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
                        val capture = ImageCapture.Builder().build()

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                preview,
                                capture
                            )
                            imageCapture.value = capture
                        } catch (e: Exception) {
                            Log.e("Camera", "Use case binding failed", e)
                        }
                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
      // AI Hint
            Text(
                text = "Align your food inside the frame",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Floating Capture Button
            FloatingActionButton(
                onClick = {
                    val capture = imageCapture.value ?: return@FloatingActionButton
//                    val outputOptions = ImageCapture.OutputFileOptions.Builder(
//                        context.cacheDir.resolve("${System.currentTimeMillis()}.jpg")
//                    ).build()

                    capture.takePicture(ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                                val buffer = imageProxy.planes[0].buffer
                                val bytes = ByteArray(buffer.remaining())
                                buffer.get(bytes)
                                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                // Resize the bitmap to 192x192
                                val targetWidth = 192
                                val targetHeight = 192
                                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

                                // Convert Bitmap to ByteBuffer
                                val inputSize = targetWidth * targetHeight * 3 // 3 for RGB channels
                                val byteBuffer = ByteBuffer.allocateDirect(inputSize * 4) // 4 bytes for float
                                byteBuffer.order(ByteOrder.nativeOrder())

                                val intValues = IntArray(targetWidth * targetHeight)
                                resizedBitmap.getPixels(intValues, 0, targetWidth, 0, 0, targetWidth, targetHeight)

                                // Convert pixel values to uint8 and apply quantization
                                for (i in intValues.indices) {
                                    val value = intValues[i]
                                    val r = ((value shr 16) and 0xFF)
                                    val g = ((value shr 8) and 0xFF)
                                    val b = (value and 0xFF)

                                    // Apply quantization
                                    byteBuffer.putFloat(((r - 128) * 0.0078125).toFloat())
                                    byteBuffer.putFloat(((g - 128) * 0.0078125).toFloat())
                                    byteBuffer.putFloat(((b - 128) * 0.0078125).toFloat())
                                }

                                // Reset the ByteBuffer for reading
                                byteBuffer.rewind()

                                // Now pass the ByteBuffer to TensorFlow Lite
                                onImageCapture(resizedBitmap)
                                imageProxy.close()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("Camera", "Capture failed", exception)
                            }
                        }
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Camera, contentDescription = "Capture")
            }
        }

            // Loading Overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x88000000)) // Semi-transparent overlay
                ){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                    )
                }
            }
        }
