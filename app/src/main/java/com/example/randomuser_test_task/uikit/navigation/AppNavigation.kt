package com.example.randomuser_test_task.uikit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.randomuser_test_task.feature.generateuser.GenerateUserScreen
import com.example.randomuser_test_task.feature.userdetail.UserDetailScreen
import com.example.randomuser_test_task.feature.userslist.UsersListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.GenerateUserScreen.route
    ) {
        composable(route = Screen.GenerateUserScreen.route) {
            GenerateUserScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToUserList = {
                    navController.navigate("user_list_screen") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Screen.UserListScreen.route) {
            UsersListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToUserDetail = { user ->
                    navController.navigate("user_detail_screen/${user.id}") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.UserDetailScreen.routeWithArgs,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(
                userId = userId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object GenerateUserScreen : Screen("generate_user_screen")
    object UserListScreen : Screen("user_list_screen")
    object UserDetailScreen : Screen("user_detail_screen") {
        const val routeWithArgs = "user_detail_screen/{userId}"
        fun createRoute(userId: String) = "user_detail_screen/$userId"
    }
}