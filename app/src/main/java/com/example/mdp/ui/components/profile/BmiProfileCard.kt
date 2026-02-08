package com.example.mdp.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mdp.ui.components.profile.bmicomponent.logic.calculateBMI
import com.example.mdp.ui.components.profile.bmicomponent.fields.NumberIntField
import com.example.mdp.ui.components.profile.bmicomponent.fields.WeightHeightField
import com.example.mdp.ui.components.profile.bmicomponent.logic.categorizeBMI
import com.example.mdp.ui.components.profile.bmicomponent.viewmodel.BmiViewModel


@Composable
fun bmiCalculator(bmiViewModel: BmiViewModel = viewModel()) {
    val age = bmiViewModel.age
    val weight = bmiViewModel.weight
    val height = bmiViewModel.height
    val gender = bmiViewModel.gender
    val bmi = bmiViewModel.bmi

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,

            ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "BMI Calculator", style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 70.dp)
                )

                // Age number field
                NumberIntField(
                    value = age,
                    onValueChange = { bmiViewModel.age = it },
                    label = { Text("Age") },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(start = 50.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text("Gender: ", style = MaterialTheme.typography.bodyLarge)
                    RadioButton(
                        selected = gender == "male",
                        onClick = { bmiViewModel.gender = "male" },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Male", style = MaterialTheme.typography.bodyLarge)
                    RadioButton(
                        selected = gender == "female",
                        onClick = { bmiViewModel.gender = "female" },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Female", style = MaterialTheme.typography.bodyLarge)
                }

                // Weight and height fields
                WeightHeightField(
                    weight = weight,
                    height = height,
                    onWeightChange = { bmiViewModel.weight = it },
                    onHeightChange = { bmiViewModel.height = it },
                )

                // Calculate BMI
                bmiViewModel.bmi = calculateBMI(weight, height)

                Row {
                    // Display BMI
                    Text(
                        text = "Your BMI: %.2f".format(bmi),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .padding(start = 50.dp)
                    )

                    Text(
                        text = "Category: ${categorizeBMI(bmi, age, gender)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 50.dp)
                    )
                }
            }
        }
    }
}