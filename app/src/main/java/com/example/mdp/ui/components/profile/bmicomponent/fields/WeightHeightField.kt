package com.example.mdp.ui.components.profile.bmicomponent.fields

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeightHeightField(
    weight: Double,
    height: Double,
    onWeightChange: (Double) -> Unit,
    onHeightChange: (Double) -> Unit
) {

    NumberDoubleField(
        value = weight,
        onValueChange = { onWeightChange(it) },
        label = { Text("Weight (kg)") },
        modifier = Modifier.padding( start = 50.dp)
    )

    NumberDoubleField(
        value = height,
        onValueChange = { onHeightChange(it) },
        label = { Text("Height (m)") },
        modifier = Modifier.padding( start = 50.dp)
    )
}