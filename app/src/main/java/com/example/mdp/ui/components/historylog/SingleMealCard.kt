package com.example.mdp.ui.components.historylog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


//hard coded values for meals(image,meal description calories, etc...),
//to be changed later in development
// to do :putting string into string resource file
@Composable
fun SingleMealCard(mealname: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))

        ) {
            Text(
                text = mealname,
                style = MaterialTheme.typography.titleLarge
            )
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Replace with actual food image
                contentDescription = "Food Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(8.dp))
                )
            Spacer(modifier = Modifier.size(8.dp)) // Add space between image and text

            Text(
                text = "Description: This is a delicious meal",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Calories: 300 kcal",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Carbs: 45g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Fat: 12g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Protein: 10g",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}