package com.example.randomuser_test_task.feature.userslist

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.randomuser_test_task.feature.Screen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    navController: NavController,
    viewModel: UsersListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UserListScreenView(state = state, onEvent = viewModel::onEvent)
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UsersListEvent.OnFetchData)
        viewModel.sideEffect.collectLatest { sideEffect ->
            handleSideEffect(sideEffect = sideEffect, navController = navController)
        }
    }
}

private fun handleSideEffect(sideEffect: UsersListSideEffect, navController: NavController) {
    when (sideEffect) {
        is UsersListSideEffect.NavigateToUserDetail ->
            navController.navigate(Screen.UserDetailScreen.createRoute(sideEffect.user.id))
        is UsersListSideEffect.NavigateToGenerateUser -> {
            navController.navigate(
                Screen.GenerateUserScreen.createRoute(isCameFromList = true)
            )
        }
    }
}

