package com.example.randomuser_test_task.feature.userdetail

import android.app.Activity
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.core.net.toUri
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String,
    navController: NavController,
    viewModel: UserDetailViewModel = koinViewModel(
        parameters = { parametersOf(userId)}
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val activity = LocalContext.current as Activity
    UserDetailScreenView(state = state, onEvent = viewModel::onEvent)
    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            handleSideEffect(
                sideEffect = effect, activity = activity, navController = navController
            )
        }
    }
}

private fun handleSideEffect(
    activity: Activity,
    sideEffect: UserDetailSideEffect,
    navController: NavController
) {
    when (sideEffect) {
        UserDetailSideEffect.Finish -> navController.popBackStack()
        is UserDetailSideEffect.MakePhoneCall -> {
            try {
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_DIAL,
                    "tel:${sideEffect.phoneNumber}".toUri()
                )
                activity.startActivity(intent)
            } catch (e: Exception) {
                Toast
                    .makeText(activity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        is UserDetailSideEffect.SendEmail -> {
            try {
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_SENDTO,
                    "mailto:${sideEffect.email}".toUri()
                )
                activity.startActivity(intent)
            } catch (e: Exception) {
                Toast
                    .makeText(activity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        is UserDetailSideEffect.OpenLocation -> {
            try {
                val uri =
                    "geo:${sideEffect.latitude},${sideEffect.longitude}?q=${sideEffect.latitude},${sideEffect.longitude}"
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    uri.toUri()
                )
                activity.startActivity(intent)
            } catch (e: Exception) {
                Toast
                    .makeText(activity, e.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}