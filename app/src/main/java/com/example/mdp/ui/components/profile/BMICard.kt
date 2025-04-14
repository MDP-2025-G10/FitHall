package com.example.mdp.ui.components.profile

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp.firebase.firestore.model.User

@Composable
fun BMICard(user: User?) {

    val height = user?.height ?: 200f
    val weight = user?.weight ?: 70f
    Log.d("BMI", "height:$height, weight:$weight")

    val bmi =
        weight / ((height / 100f) * (height / 100f)) // BMI = kg / (m^2)
    val bmiCategory = when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }

    val categoryColors = listOf(
        Color(0xFF87CEFA), // Underweight
        Color(0xFF90EE90), // Normal
        Color(0xFFFFD700), // Overweight
        Color(0xFFFF6347)  // Obese
    )
    val categoryLabels = listOf("Underweight", "Normal", "Overweight", "Obese")
    val segmentPercentages = listOf(18.5f, 6.5f, 5f, 10f).map { it / 40f }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BMI info",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "BMI: %.1f (%s)".format(bmi, bmiCategory),
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            // Progress bar
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {
                val totalWidth = size.width
                val barHeight = size.height
                val cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())

                var startX = 0f
                for (i in segmentPercentages.indices) {
                    val segmentWidth = totalWidth * segmentPercentages[i]
                    drawRoundRect(
                        color = categoryColors[i],
                        topLeft = Offset(startX, 0f),
                        size = Size(segmentWidth, barHeight),
                        cornerRadius = cornerRadius
                    )
                    startX += segmentWidth
                }

                // Marker line
                val markerX = (bmi.coerceIn(0f, 40f) / 40f) * totalWidth
                drawLine(
                    color = Color.Black,
                    start = Offset(markerX, 0f),
                    end = Offset(markerX, barHeight),
                    strokeWidth = 4f
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                categoryLabels.forEachIndexed { i, label ->
                    Text(label, fontSize = 12.sp, color = categoryColors[i])
                }
            }
        }
    }
}
