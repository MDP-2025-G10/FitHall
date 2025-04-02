package com.example.mdp.ui.components.food


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MacroProgressBar(carbs: Int, protein: Int, fats: Int) {
    val total = carbs + protein + fats
    val carbPercentage = if (total > 0) carbs / total.toFloat() else 0f
    val proteinPercentage = if (total > 0) protein / total.toFloat() else 0f
    val fatPercentage = if (total > 0) fats / total.toFloat() else 0f

    val carbColor = Color(0xFFFFD462)
    val proteinColor = Color(0xFF607ABF)
    val fatColor = Color(0xFFFF9B78)

    Column(modifier = Modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        ) {
            val barWidth = size.width
            val barHeight = size.height
            val cornerRadius = CornerRadius(2.dp.toPx(), 0.dp.toPx())

            var startX = 0f

            // Draw Carbs section
            val carbWidth = barWidth * carbPercentage
            drawRoundRect(
                color = carbColor,
                topLeft = androidx.compose.ui.geometry.Offset(startX, 0f),
                size = Size(carbWidth, barHeight),
                cornerRadius = cornerRadius
            )
            startX += carbWidth

            // Draw Protein section
            val proteinWidth = barWidth * proteinPercentage
            drawRoundRect(
                color = proteinColor,
                topLeft = androidx.compose.ui.geometry.Offset(startX, 0f),
                size = Size(proteinWidth, barHeight),
                cornerRadius = cornerRadius
            )
            startX += proteinWidth

            // Draw Fats section
            val fatWidth = barWidth * fatPercentage
            drawRoundRect(
                color = fatColor,
                topLeft = androidx.compose.ui.geometry.Offset(startX, 0f),
                size = Size(fatWidth, barHeight),
                cornerRadius = cornerRadius
            )
        }

        // Legend
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            LegendItem(color = carbColor, label = "Carbs", percentage = carbPercentage)
            LegendItem(color = proteinColor, label = "Protein", percentage = proteinPercentage)
            LegendItem(color = fatColor, label = "Fats", percentage = fatPercentage)
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String, percentage: Float) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(12.dp)) {
            drawCircle(color)
        }
        Text(
            text = "$label ${"%.1f".format(percentage * 100)}%",
            modifier = Modifier.padding(start = 4.dp),
            fontSize = 14.sp
        )

    }
}
