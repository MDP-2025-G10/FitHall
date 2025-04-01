package com.example.mdp.ui.components.food

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.mdp.data.model.Meal
import com.example.mdp.imgur.viewmodel.ImgurViewModel
import com.example.mdp.navigation.LocalImgurViewModel
import com.example.mdp.navigation.LocalMealViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody


@Composable
fun FoodPopUp(meal: Meal, onDismiss: () -> Unit) {
    val mealViewModel = LocalMealViewModel.current
    val imgurViewModel = LocalImgurViewModel.current

    var mealName by remember { mutableStateOf(meal.name) }
    val calories by remember { mutableStateOf(meal.calories.toString()) }
    val fats by remember { mutableStateOf(meal.fats.toString()) }
    val carbs by remember { mutableStateOf(meal.carbs.toString()) }
    val protein by remember { mutableStateOf(meal.proteins.toString()) }

    var imagePath by remember { mutableStateOf(meal.imagePath) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                uploadImageToImgur(context, uri, imgurViewModel) { uploadedLink ->
                    imagePath = uploadedLink // Store the Imgur link for saving the meal
                }
            }
        }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Meal") },
        text = {
            Column {
                OutlinedTextField(
                    value = mealName,
                    onValueChange = { mealName = it },
                    label = { Text("Meal Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = calories,
                    onValueChange = { it.toIntOrNull() ?: 0 },
                    label = { Text("Calories (kcal)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = fats,
                    onValueChange = { it.toIntOrNull() ?: 0 },
                    label = { Text("Fats (g)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = carbs,
                    onValueChange = { it.toIntOrNull() ?: 0 },
                    label = { Text("Carbs (g)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = protein,
                    onValueChange = { it.toIntOrNull() ?: 0 },
                    label = { Text("Proteins (g)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Pick Image Button
                Button(onClick = { imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                    Text("Select Photo")
                }

            }

        },
        confirmButton = {
            Button(onClick = {
                if (mealName.isNotEmpty() && imagePath.isNotEmpty()) {
                    val newMeal = Meal(
                        name = mealName,
                        calories = calories.toIntOrNull() ?: 0,
                        fats = fats.toIntOrNull() ?: 0,
                        carbs = carbs.toIntOrNull() ?: 0,
                        proteins = protein.toIntOrNull() ?: 0,
                        imagePath = imagePath,
                        timestamp = System.currentTimeMillis()
                    )
                    mealViewModel.insertMeal(newMeal, imagePath)
                    onDismiss()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


fun uploadImageToImgur(
    context: Context,
    uri: Uri,
    imgurViewModel: ImgurViewModel,
    onUploaded: (String) -> Unit
) {
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        val byteArray = inputStream.readBytes()
        val requestBody = byteArray.toRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", "upload.jpg", requestBody)

        imgurViewModel.uploadImage(body) { result ->
            result.onSuccess { imgurResponse ->
                val uploadedUrl = imgurResponse.data.link
                Log.d("FoodPopUp", "Uploaded Image URL: $uploadedUrl") // Debug Log
                onUploaded(uploadedUrl)
            }.onFailure { error ->
                Log.e("FoodPopUp", "Error uploading image: ${error.message}")
            }
        }
    } ?: Log.e("FoodPopUp", "Failed to open input stream")
}

