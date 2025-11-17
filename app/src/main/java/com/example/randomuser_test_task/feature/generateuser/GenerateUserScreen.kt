package com.example.randomuser_test_task.feature.generateuser

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import com.example.randomuser_test_task.feature.Screen
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateUserScreen(
    navController: NavController,
    cameFromUserList: Boolean,
    viewModel: GenerateUserViewModel = koinViewModel<GenerateUserViewModel>(
        parameters = { parametersOf(cameFromUserList) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GenerateUserScreenView(state = state, onEvent = viewModel::onEvent)
    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is GenerateUserSideEffect.NavigateToUserList -> {
                    if (sideEffect.cameFromUserList) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(Screen.UserListScreen.route) {
                            popUpTo(Screen.GenerateUserScreen.route) { inclusive = true }
                        }
                    }
                }
                GenerateUserSideEffect.Finish -> navController.popBackStack()
            }
        }
    }
}