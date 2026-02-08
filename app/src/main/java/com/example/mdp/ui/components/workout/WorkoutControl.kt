package com.example.mdp.ui.components.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WorkoutControl(
    weightInput: String,
    repsInput: String,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val weight = weightInput.toDoubleOrNull() ?: 0.0
        InputRaw(
            label = "Weight (kgs)",
            value = weightInput,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d{0,3}(\\.\\d?)?\$"))) {
                    onWeightChange(newValue)
                }
            },
            onIncrease = { onWeightChange((weight + 2.5).toString()) },
            onDecrease = { if (weight > 0) onWeightChange((weight - 2.5).toString()) }
        )

        val reps = repsInput.toIntOrNull() ?: 0
        InputRaw(
            label = "Reps",
            value = repsInput,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d{0,2}$"))) {
                    onRepsChange(newValue)
                }
            },
            onIncrease = { onRepsChange((reps + 1).toString()) },
            onDecrease = { if (reps > 0) onRepsChange((reps - 1).toString()) }
        )
        Interaction(
            onSave = onSave,
            onClear = {
                onWeightChange("")
                onRepsChange("")
            }
        )
    }
}