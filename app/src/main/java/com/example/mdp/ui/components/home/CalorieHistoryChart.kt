package com.example.mdp.ui.components.home

import android.util.Log
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.mdp.navigation.LocalMealViewModel
import com.example.mdp.utils.lineChartTimeFormatter
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries


@Composable
fun CalorieHistoryChart() {

    val mealViewModel = LocalMealViewModel.current
    val modelProducer = remember { CartesianChartModelProducer() }
    val calorieHistory by mealViewModel.calorieHistory.collectAsState()

    Log.d("calorieHistory", "calorieHistory: $calorieHistory")

    LaunchedEffect(calorieHistory) {
        if (calorieHistory.isNotEmpty()) {
            val xValues = (1..calorieHistory.size).map { it }
            val yValues = calorieHistory.map { it.totalCalories }

            Log.d("chart", "$xValues")
            Log.d("chart", "$yValues")
            modelProducer.runTransaction {
                lineSeries { series(xValues, yValues) }
            }
        } else {
            // Provide default values to prevent the chart from crashing
            val xValues = (1..7).toList() // Assuming 7 days for the last week
            val yValues = List(7) { 0 } // Placeholder for zero calories for 7 days

            Log.d("chart", "Default xValues: $xValues")
            Log.d("chart", "Default yValues: $yValues")

            // Update the model producer with placeholder data
            modelProducer.runTransaction {
                lineSeries { series(xValues, yValues) }
            }
        }
    }

    Card {
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = { _, value, _ ->
                        val index = value.toInt() - 1
                        if (index in calorieHistory.indices) {
                            val dateStr = calorieHistory[index].date
                            lineChartTimeFormatter(dateStr)
                        } else {
                            // Return a valid placeholder for out-of-bounds indices
                            "Day ${index + 1}" // Using Day N for placeholder
                        }
                    }
                ),
            ),
            modelProducer,
        )
    }
}

