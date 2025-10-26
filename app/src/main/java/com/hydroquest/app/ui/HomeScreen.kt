package com.hydroquest.app.ui

import android.annotation.SuppressLint
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
import com.hydroquest.app.model.LogIntakeRequest
import com.hydroquest.app.network.RetrofitClient
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    userId: String,
    dailyGoalState: MutableState<Int>,
    unitsState: MutableState<String>
) {
    val coroutineScope = rememberCoroutineScope()
    var dailyIntake by remember { mutableStateOf(0) }


    LaunchedEffect(userId) {
        try {
            val response = RetrofitClient.apiService.getDailyIntake(userId)
            if (response.isSuccessful) {
                dailyIntake = response.body() ?: 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7)))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Daily Intake", style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(
                        progress = (dailyIntake.toFloat() / dailyGoalState.value).coerceIn(0f, 1f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        color = Color(0xFF0ED2F7),
                        trackColor = Color(0xFFE0E0E0)
                    )
                    Text(
                        "You have consumed $dailyIntake ${unitsState.value} of ${dailyGoalState.value} ${unitsState.value}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    RetrofitClient.apiService.logIntake(
                                        LogIntakeRequest(userId, 250)
                                    )
                                    dailyIntake += 250
                                    RetrofitClient.apiService.updateGamification(userId, 250)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Log 250 ${unitsState.value}")
                    }
                }
            }
            //buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("trends/$userId") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Trends")
                }

                Button(
                    onClick = { navController.navigate("quest/$userId") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to Quest")
                }

                Button(
                    onClick = { navController.navigate("settings/$userId") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Settings")
                }
            }
        }
    }
}


