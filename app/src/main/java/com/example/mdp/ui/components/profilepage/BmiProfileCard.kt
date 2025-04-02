package com.example.mdp.ui.components.profilepage

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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp.ui.components.profilepage.bmicomponent.logic.calculateBMI
import com.example.mdp.ui.components.profilepage.bmicomponent.fields.NumberIntField
import com.example.mdp.ui.components.profilepage.bmicomponent.fields.WeightHeightField
import com.example.mdp.ui.components.profilepage.bmicomponent.logic.categorizeBMI


@Composable
fun Bmicalculator() {
    val age = remember { mutableIntStateOf(0) }
    val weight = remember { mutableDoubleStateOf(0.0) }
    val height = remember { mutableDoubleStateOf(0.0) }
    val bmi = remember { mutableDoubleStateOf(0.0) }
    val gender = remember { mutableStateOf("male") }

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
                    value = age.intValue,
                    onValueChange = { age.intValue = it },
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
                        selected = gender.value == "male",
                        onClick = { gender.value = "male" },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Male", style = MaterialTheme.typography.bodyLarge)
                    RadioButton(
                        selected = gender.value == "female",
                        onClick = { gender.value = "female" },
                        colors = RadioButtonDefaults.colors()
                    )
                    Text("Female", style = MaterialTheme.typography.bodyLarge)
                }

                // Weight and height fields
                WeightHeightField(
                    weight = weight.doubleValue,
                    height = height.doubleValue,
                    onWeightChange = { weight.doubleValue = it },
                    onHeightChange = { height.doubleValue = it },

                    )

                // Calculate BMI
                bmi.doubleValue = calculateBMI(weight.doubleValue, height.doubleValue)


                Row {
                    // Display BMI
                    Text(
                        text = "Your BMI: %.2f".format(bmi.doubleValue),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .padding(start = 50.dp)
                    )

                    Text(
                        text = "Category: ${categorizeBMI(bmi.doubleValue, age.intValue, gender.value)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                            .padding(start = 50.dp)
                    )
                }
            }
        }
    }
}