package com.hydroquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.hydroquest.app.ui.HomeScreen
import com.hydroquest.app.ui.LoginScreen
import com.hydroquest.app.ui.QuestScreen
import com.hydroquest.app.ui.SettingsScreen
import com.hydroquest.app.ui.TrendsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HydroQuestApp()
        }
    }
}

@Composable
fun HydroQuestApp() {
    val navController = rememberNavController()
    val dailyGoalState = remember { mutableStateOf(2000) }
    val unitsState = remember { mutableStateOf("ml") }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }

        composable("home/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HomeScreen(navController, userId, dailyGoalState, unitsState)
        }

        composable("quest/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            QuestScreen(navController, userId)
        }

        composable("trends/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            TrendsScreen(navController, userId)
        }

        composable("settings/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            SettingsScreen(navController, userId, dailyGoalState, unitsState)
        }
    }
}
