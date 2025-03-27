package com.example.mdp.ui.components.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CaloriesBar(amountsConsumed: Float, dailyAmountGoal: Float) {
    val progress = amountsConsumed / dailyAmountGoal
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Calories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = "${amountsConsumed.toInt()}/${dailyAmountGoal.toInt()}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        CustomProgressBar(progress)
    }
}

@Composable
fun CustomProgressBar(progress: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        val barWidth = size.width
        val progressWidth = barWidth * progress
        val barHeight = size.height
        val cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())

        // background track
        drawRoundRect(
            color = Color(0xFF5E5E5E),
            size = Size(barWidth, barHeight),
            cornerRadius = cornerRadius
        )

        // foreground progress
        drawRoundRect(
            color = Color(0xFF9CCC65),
            size = Size(progressWidth, barHeight),
            cornerRadius = cornerRadius
        )
    }
}


