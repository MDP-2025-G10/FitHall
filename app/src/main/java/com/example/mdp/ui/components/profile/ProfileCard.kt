package com.example.mdp.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mdp.R
import com.example.mdp.firebase.firestore.model.User


@Composable
fun ProfileCard(user: User?) {
    val name = user?.name ?: "No Name"
    val email = user?.email ?: "No Email"
    val avatar = user?.profilePic
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Account info", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    model = avatar ?: R.drawable.ic_profileplaceholderimage
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = email,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}