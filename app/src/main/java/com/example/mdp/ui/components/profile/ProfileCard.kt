package com.example.mdp.ui.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mdp.R
import com.example.mdp.firebase.firestore.model.User


@Composable
fun ProfileCard(user: User?, onUpdateUser: (User) -> Unit) {
    val name = remember { mutableStateOf(user?.name ?: "No Name") }
    val email = remember { mutableStateOf(user?.email ?: "No Email") }
    val avatar = user?.profilePic
    val isEditing = remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        user?.let {
            name.value = it.name
            email.value = it.email
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
                text = "Account info",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 20.dp)
            )
            IconButton(onClick = {
                if (isEditing.value && user != null) {
                    // Save edited user data
                    onUpdateUser(user.copy(name = name.value, email = email.value))
                }
                isEditing.value = !isEditing.value
            }
            ) {
                Icon(
                    imageVector = if (isEditing.value) Icons.Outlined.Save else Icons.Outlined.Edit,
                    contentDescription = if (isEditing.value) "Save Profile" else "Edit Profile",
                    modifier = Modifier.size(20.dp),
                    tint = Color.DarkGray
                )
            }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (isEditing.value) {
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        maxLines = 1,
                        singleLine = true,
                    )
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        singleLine = true,
                    )
                } else {
                    Text(
                        text = name.value,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = email.value,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}