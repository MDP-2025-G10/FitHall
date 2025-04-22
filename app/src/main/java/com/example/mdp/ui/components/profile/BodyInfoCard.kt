package com.example.mdp.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp.firebase.firestore.model.User


@Composable
fun BodyInfoCard(user: User?, onUpdateUser: (User) -> Unit) {

    val height = remember { mutableStateOf(user?.height?.toString() ?: "") }
    val weight = remember { mutableStateOf(user?.weight?.toString() ?: "") }
    val gender = remember { mutableStateOf(user?.gender ?: "") }
    val age = remember { mutableStateOf(user?.age?.toString() ?: "") }
    val birthday = remember { mutableStateOf(user?.birthday ?: "") }
    val isEditing = remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        user?.let {
            height.value = it.height.toString()
            weight.value = it.weight.toString()
            birthday.value = it.birthday
            age.value = it.age.toString()
            gender.value = it.gender
        }
    }

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
                text = "Body info",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 20.dp)
            )
            IconButton(onClick = {
                if (isEditing.value && user != null) {
                    onUpdateUser(
                        user.copy(
                            height = height.value.toFloatOrNull() ?: user.height,
                            weight = weight.value.toFloatOrNull() ?: user.weight,
                            birthday = birthday.value,
                            age = age.value.toIntOrNull() ?: user.age,
                            gender = gender.value
                        )
                    )
                }
                isEditing.value = !isEditing.value
            }
            ) {
                Icon(
                    imageVector = if (isEditing.value) Icons.Outlined.Save else Icons.Outlined.Edit,
                    contentDescription = if (isEditing.value) "Save Body Info" else "Edit Body Info",
                    modifier = Modifier.size(20.dp),
                    tint = Color.DarkGray
                )
            }
        }


        Column(modifier = Modifier.padding(top = 8.dp)) {
            if (isEditing.value) {
                EditableInfoField(label = "Age", value = age.value) { age.value = it }
                EditableInfoField(label = "Height (cm)", value = height.value) { height.value = it }
                EditableInfoField(label = "Weight (kg)", value = weight.value) { weight.value = it }
                EditableInfoField(label = "Birthday", value = birthday.value) {
                    birthday.value = it
                }
                GenderSelector(gender = gender.value) { gender.value = it }
            } else {
                InfoRow("Age", age.value)
                InfoRow("Height", "${
                    height.value.ifBlank { "-" }
                } cm")
                InfoRow("Weight", "${
                    weight.value.ifBlank { "-" }
                } kg")
                InfoRow("Birthday", birthday.value)
                InfoRow("Gender", gender.value.ifBlank { "-" })
            }
        }

    }

}

@Composable
fun EditableInfoField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(2f),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun GenderSelector(gender: String, onGenderSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Gender:", fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender.lowercase() == "male",
                onClick = { onGenderSelected("male") }
            )
            Text("Male", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = gender.lowercase() == "female",
                onClick = { onGenderSelected("female") }
            )
            Text("Female")

            RadioButton(
                selected = gender.lowercase() == "others",
                onClick = { onGenderSelected("others") }
            )
            Text("Others")
        }
    }
}


