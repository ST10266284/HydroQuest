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
import com.hydroquest.app.model.UpdateSettingsRequest
import com.hydroquest.app.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    userId: String,
    dailyGoalState: MutableState<Int>,
    unitsState: MutableState<String>
) {
    val coroutineScope = rememberCoroutineScope()
    var dailyGoal by remember { mutableStateOf(dailyGoalState.value.toString()) }
    var units by remember { mutableStateOf(unitsState.value) }
    var theme by remember { mutableStateOf("light") }

    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7)))),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, containerColor = Color.Transparent) { paddingValues ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(paddingValues),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Settings", style = MaterialTheme.typography.titleMedium)
                    TextField(
                        value = dailyGoal,
                        onValueChange = { dailyGoal = it },
                        label = { Text("Daily Goal") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = units,
                        onValueChange = { units = it },
                        label = { Text("Units") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = theme,
                        onValueChange = { theme = it },
                        label = { Text("Theme") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                try {
                                    val goal = dailyGoal.toIntOrNull() ?: 2000
                                    RetrofitClient.apiService.updateSettings(
                                        userId,
                                        UpdateSettingsRequest(goal, units, theme)
                                    )
                                    dailyGoalState.value = goal
                                    unitsState.value = units
                                    snackbarHostState.showSnackbar("Settings saved successfully!")
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    snackbarHostState.showSnackbar("Failed to save settings")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Settings")
                    }
                    Button(
                        onClick = { navController.navigate("home/$userId") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Back to Home", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}



