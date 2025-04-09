package com.example.mdp.ui.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp.firebase.firestore.model.User


@Composable
fun UserInfoCard(user: User?) {

    val height = user?.height.toString()
    val weight = user?.weight.toString()
    val birthday = user?.birthday.toString()
    val age = user?.age.toString()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val infoList = listOf(
            "Age" to age,
            "Height" to "$height cm",
            "Weight" to "$weight kg",
            "Birthday" to birthday
        )


        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Body info", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.padding(8.dp)) {
            infoList.forEach { (label, value) ->
                InfoRow(label = label, value = value)
            }
        }

    }

}

@Composable
fun InfoRow(label: String, value: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            modifier = Modifier.weight(2f)
        )
    }
}
