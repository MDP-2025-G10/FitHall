package com.example.mdp.ui.components.assets

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

fun getNutritionInfo(foodName: String, onResponse: (String) -> Unit) {
//    val url = "https://api.nal.usda.gov/fdc/v1/foods/search?query=$foodName&api_key=${BuildConfig.USDA_API_KEY}"
    val url = "https://world.openfoodfacts.org/api/v0/product/$foodName.json"

    val request = Request.Builder().url(url).build()
    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("API", "Failed to fetch data")
        }

        override fun onResponse(call: Call, response: Response) {
            response.body()?.string()?.let {
                val json = JSONObject(it)
                val calories = json.optJSONObject("product")
                    ?.optJSONObject("nutriments")
                    ?.optString("energy-kcal", "0.0")

                onResponse("Calories: $calories kcal")
            }
        }
    })
}