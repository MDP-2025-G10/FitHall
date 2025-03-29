package com.example.mdp.ui.components.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.navigation.LocalDateViewModel
import com.example.mdp.navigation.NavRoutes
import com.example.mdp.utils.dateSelectorTimeFormatter

@Composable
fun DateSelector(navController: NavController) {

    val dateViewModel = LocalDateViewModel.current

    val selectedDate by dateViewModel.selectedDate
    val today by dateViewModel.today

    val formattedDate = dateSelectorTimeFormatter(selectedDate)

    Box(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            }
            .height(56.dp)
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                val newDate = selectedDate.minusDays(1)
                dateViewModel.setSelectedDate(newDate)
                navController.navigate(NavRoutes.RouteToFood.route) {
                    popUpTo(NavRoutes.RouteToFood.route) { inclusive = false }
                }

            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "previous date"
                )
            }

            Text(
                text = when (selectedDate) {
                    today -> "Today"
                    today.minusDays(1) -> "Yesterday"
                    today.plusDays(1) -> "Tomorrow"
                    else -> formattedDate
                }

            )

            IconButton(onClick = {
                val newDate = selectedDate.plusDays(1)
                dateViewModel.setSelectedDate(newDate)
                navController.navigate(NavRoutes.RouteToFood.route) {
                    popUpTo(NavRoutes.RouteToFood.route) { inclusive = false }
                }


            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "next date"
                )
            }
        }
    }

}