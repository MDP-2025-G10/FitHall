package com.example.mdp.ui.components.profilepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mdp.R


@Composable
fun ProfileCard(userName: String, email: String, avatar: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
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
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}