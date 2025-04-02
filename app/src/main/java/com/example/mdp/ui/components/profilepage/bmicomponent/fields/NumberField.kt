package com.example.mdp.ui.components.profilepage.bmicomponent.fields

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun NumberIntField(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = value.toString(),
        onValueChange = { newValue -> onValueChange(newValue.toIntOrNull() ?: 0) },
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
    TextField(
        value = value.toString(),
        onValueChange = { newValue -> onValueChange(newValue.toDoubleOrNull() ?: 0.0) },
        label = label,
        modifier = modifier
    )
}