package com.example.randomuser_test_task.uikit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.randomuser_test_task.feature.generateuser.GenerateUserScreen
import com.example.randomuser_test_task.feature.userdetail.UserDetailScreen
import com.example.randomuser_test_task.feature.userslist.UsersListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.GenerateUserScreen.route
    ) {
        composable(Screen.GenerateUserScreen.route) {
            GenerateUserScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToUserList = {
                    navController.navigate("userList")
                }
            )
        }
        composable(Screen.UserListScreen.route) {
            UsersListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToUserDetail = { user ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("userId", user.id)
                    navController.navigate(Screen.UserDetailScreen.route)
                }
            )
        }
        composable(Screen.UserDetailScreen.route) {
            val userId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("userId") ?: ""
            UserDetailScreen(
                userId = userId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object GenerateUserScreen : Screen("generate_user_screen")
    object UserListScreen : Screen("user_list_screen")
    object UserDetailScreen : Screen("user_detail_screen")
}