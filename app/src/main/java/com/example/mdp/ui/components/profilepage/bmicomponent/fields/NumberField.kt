package com.example.mdp.ui.components.profilepage.bmicomponent.fields

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun NumberIntField(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = { newValue ->
            val intValue = newValue.toIntOrNull()
            if (intValue != null && intValue >= 0) {
                onValueChange(intValue)
            } else if (newValue.isEmpty()) {
                onValueChange(0)
            }
        },
        label = label,
        modifier = modifier
    )
}

@Composable
fun NumberDoubleField(
    value: Double,
    onValueChange: (Double) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = if (value == 0.0) "" else value.toString(),
        onValueChange = { newValue ->
            val doubleValue = newValue.toDoubleOrNull()
            if (doubleValue != null && doubleValue >= 0.0) {
                onValueChange(doubleValue)
            } else if (newValue.isEmpty()) {
                onValueChange(0.0)
            }
        },
        label = label,
        modifier = modifier
    )
}