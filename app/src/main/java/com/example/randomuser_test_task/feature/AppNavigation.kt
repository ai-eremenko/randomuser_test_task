package com.example.randomuser_test_task.feature

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.randomuser_test_task.feature.generateuser.GenerateUserScreen
import com.example.randomuser_test_task.feature.userdetail.UserDetailScreen
import com.example.randomuser_test_task.feature.userslist.UsersListScreen

private const val IS_CAME_FROM_LIST_ARGUMENT = "IS_CAME_FROM_LIST_ARGUMENT"
private const val USER_ID_ARGUMENT = "USER_ID_ARGUMENT"

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.GenerateUserScreen.route
    ) {
        composable(
            route = Screen.GenerateUserScreen.route,
            arguments = listOf(
                navArgument(IS_CAME_FROM_LIST_ARGUMENT) {
                    type = NavType.BoolType
                }
            )
        ) {backStackEntry ->
            val cameFromUserList =
                backStackEntry.arguments?.getBoolean(IS_CAME_FROM_LIST_ARGUMENT) ?: false
            GenerateUserScreen(navController = navController, cameFromUserList = cameFromUserList)
        }
        composable(route = Screen.UserListScreen.route) {
            UsersListScreen(navController = navController)
        }
        composable(
            route = Screen.UserDetailScreen.route,
            arguments = listOf(
                navArgument(USER_ID_ARGUMENT) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(USER_ID_ARGUMENT).orEmpty()
            UserDetailScreen(
                navController = navController,
                userId = userId
            )
        }
    }
}

sealed class Screen(val route: String) {
    object GenerateUserScreen : Screen("generate_user_screen/{$IS_CAME_FROM_LIST_ARGUMENT}") {
        fun createRoute(isCameFromList: Boolean) = "generate_user_screen/$isCameFromList"
    }
    object UserListScreen : Screen("user_list_screen")
    object UserDetailScreen : Screen("user_detail_screen/{$USER_ID_ARGUMENT}") {
        fun createRoute(userId: String) = "user_detail_screen/$userId"
    }
}