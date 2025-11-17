package com.example.randomuser_test_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.randomuser_test_task.feature.AppNavigation
import com.example.randomuser_test_task.uikit.theme.RandomUserTestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserTestTaskTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}