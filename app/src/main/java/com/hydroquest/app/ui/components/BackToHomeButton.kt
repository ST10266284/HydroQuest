package com.hydroquest.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BackToHomeButton(navController: NavController, userId: String) {
    Button(
        onClick = {
            navController.navigate("home/$userId") {
                popUpTo("home/$userId") { inclusive = true }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90EE90))
    ) {
        Text("Back to Home")
    }
}
