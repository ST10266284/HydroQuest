package com.hydroquest.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hydroquest.app.network.RetrofitClient
import com.hydroquest.app.ui.components.BackToHomeButton
import kotlinx.coroutines.launch

@Composable
fun QuestScreen(navController: NavController, userId: String) {
    val coroutineScope = rememberCoroutineScope()
    var streak by remember { mutableStateOf(0) }
    var companionLevel by remember { mutableStateOf(1) }
    var achievements by remember { mutableStateOf("") }

    fun refreshGamification() {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.apiService.getGamification(userId)
                if (response.isSuccessful) {
                    val game = response.body()
                    streak = game?.streak ?: 0
                    companionLevel = game?.companionLevel ?: 1
                    achievements = game?.achievements ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    LaunchedEffect(Unit) { refreshGamification() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quest Progress",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.DarkGray
            )


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Streak", style = MaterialTheme.typography.titleMedium)
                    Text("$streak days", style = MaterialTheme.typography.bodyMedium)
                }
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Companion Level", style = MaterialTheme.typography.titleMedium)
                    Text("$companionLevel", style = MaterialTheme.typography.bodyMedium)
                }
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Achievements", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (achievements.isEmpty()) "None yet" else achievements,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { refreshGamification() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90EE90))
            ) {
                Text("Refresh")
            }
            BackToHomeButton(navController = navController, userId = userId)
        }
    }
}
