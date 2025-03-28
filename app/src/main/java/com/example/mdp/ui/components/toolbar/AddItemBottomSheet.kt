package com.example.mdp.ui.components.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mdp.R
import com.example.mdp.data.viewmodel.DateViewModel
import com.example.mdp.navigation.NavRoutes
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemBottomSheet(
    navController: NavController,
    showSheet: Boolean,
    onDismiss: () -> Unit,
) {
    val dateViewModel: DateViewModel = koinViewModel()

    val today by dateViewModel.today
    val selectedDate by dateViewModel.selectedDate

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Use CardItem for the Meal Card
                    ActionCardItem(
                        modifier = Modifier.weight(1f),
                        imageResId = R.drawable.lunch_dining_24px,
                        contentDescription = "Add Meal",
                        label = "Meal",
                        onClick = { navController.navigate(NavRoutes.routeToFood(selectedDate.toString())) },
                        tintColor = Color(0xFF7CA7B1)
                    )

                    // Use CardItem for the Workout Card
                    ActionCardItem(
                        modifier = Modifier.weight(1f),
                        imageResId = R.drawable.exercise_24px,
                        contentDescription = "Add workout",
                        label = "Workout",
                        onClick = { navController.navigate(NavRoutes.RouteToWorkout.route) },
                        tintColor = Color(0xFFAC857C)
                    )
                }


            }
        }
    }
}

@Composable
fun ActionCardItem(
    modifier: Modifier = Modifier,
    imageResId: Int,
    contentDescription: String,
    label: String,
    onClick: () -> Unit,
    tintColor: Color = Color.Unspecified
) {
    Card(
        modifier = modifier
            .padding(end = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(tintColor),
                modifier = Modifier.size(36.dp)
            )
            Text(label)
        }
    }
}
