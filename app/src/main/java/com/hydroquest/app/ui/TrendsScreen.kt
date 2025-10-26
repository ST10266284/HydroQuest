package com.hydroquest.app.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hydroquest.app.network.RetrofitClient
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun TrendsScreen(navController: NavController, userId: String) {
    val coroutineScope = rememberCoroutineScope()
    var weeklyIntake by remember { mutableStateOf<List<Float>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    val daysOfWeek = listOf("6", "5", "4", "3", "2", "1", "Today")

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.apiService.getWeeklyIntake(userId)
                if (response.isSuccessful) {
                    weeklyIntake = response.body()?.map { it.toFloat() } ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(listOf(Color(0xFFB2FEFA), Color(0xFF0ED2F7))),
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Weekly Hydration Trends",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (weeklyIntake.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No data available", color = Color.White)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0x33000000), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val maxVal = (weeklyIntake.maxOrNull() ?: 2000f).coerceAtLeast(2000f)
                    val points = weeklyIntake.mapIndexed { index, value ->
                        Offset(
                            x = index * (size.width / (weeklyIntake.size - 1)),
                            y = size.height - (value / maxVal) * size.height
                        )
                    }

                    val path = Path().apply {
                        points.forEachIndexed { index, offset ->
                            if (index == 0) moveTo(offset.x, offset.y)
                            else lineTo(offset.x, offset.y)
                        }
                    }
                    drawPath(path = path, color = Color.White, style = Stroke(width = 4f))

                    points.forEach { offset ->
                        drawCircle(Color.Yellow, radius = 6f, center = offset)
                    }

                    val step = maxVal / 4
                    for (i in 0..4) {
                        val y = size.height - (i * step / maxVal) * size.height
                        drawContext.canvas.nativeCanvas.drawText(
                            "${(i * step).toInt()}",
                            0f,
                            y,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.LEFT
                            }
                        )
                    }

                    points.forEachIndexed { index, offset ->
                        drawContext.canvas.nativeCanvas.drawText(
                            daysOfWeek.getOrElse(index) { "" },
                            offset.x,
                            size.height + 25f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.WHITE
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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


