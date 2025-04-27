package com.example.mdp.ui.components.utils

import com.example.mdp.BuildConfig.USDA_API_KEY
import com.example.mdp.data.model.NutritionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder

suspend fun getNutritionInfo(foodName: String): NutritionData? {
    val searchUrl = "https://api.nal.usda.gov/fdc/v1/foods/search?query=${URLEncoder.encode(foodName, "UTF-8")}&api_key=$USDA_API_KEY"

    val client = OkHttpClient()

    return try {
        // Search for the food
        val searchRequest = Request.Builder().url(searchUrl).build()
        val searchResponse = withContext(Dispatchers.IO) { client.newCall(searchRequest).execute() }

        if (!searchResponse.isSuccessful) return null

        val searchResult = JSONObject(searchResponse.body?.string() ?: "")
        val foodsArray = searchResult.optJSONArray("foods")
        if (foodsArray == null || foodsArray.length() == 0) return null

        val fdcId = foodsArray.getJSONObject(0).getInt("fdcId")

        // Fetch details using FDC ID
        val detailUrl = "https://api.nal.usda.gov/fdc/v1/food/$fdcId?api_key=$USDA_API_KEY"
        val detailRequest = Request.Builder().url(detailUrl).build()
        val detailResponse = withContext(Dispatchers.IO) { client.newCall(detailRequest).execute() }

        if (!detailResponse.isSuccessful) return null

        val detailJson = JSONObject(detailResponse.body?.string() ?: "")
        val nutrients = detailJson.optJSONArray("foodNutrients") ?: return null

        fun getValue(name: String): Float {
            for (i in 0 until nutrients.length()) {
                val nutrient = nutrients.getJSONObject(i)
                if (nutrient.getString("nutrientName").contains(name, ignoreCase = true)) {
                    return nutrient.optDouble("value", 0.0).toFloat()
                }
            }
            return 0f
        }

        val calories = getValue("Energy").toInt()
        val fat = getValue("Total Fat")
        val carbs = getValue("Carbohydrate")
        val protein = getValue("Protein")

        NutritionData(calories, fat, carbs, protein)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}