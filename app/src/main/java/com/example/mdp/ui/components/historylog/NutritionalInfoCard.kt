package com.example.mdp.ui.components.historylog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun NutritionalCard(label: String, value: String, icon: ImageVector) {
    Card() {
        Column() {
            Row() {
                Icon(
                    imageVector = icon,
                    contentDescription = "Nutrient Icon"
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "$label:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Row() {
                Spacer(modifier = Modifier.size(30.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}