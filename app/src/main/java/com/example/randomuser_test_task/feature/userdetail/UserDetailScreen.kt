package com.example.randomuser_test_task.feature.userdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomuser_test_task.feature.userdetail.view.UserHeaderSection
import com.example.randomuser_test_task.feature.userdetail.view.UserTabsSection
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String,
    onNavigateBack: () -> Unit,
    viewModel: UserDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onBackClick()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.user != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    UserHeaderSection(user = state.user!!)
                    UserTabsSection(
                        user = state.user!!,
                        selectedTabIndex = state.selectedTabIndex,
                        onTabSelected = { tabIndex ->
                            viewModel.onTabSelected(tabIndex)
                        },
                        makePhoneCall = { phoneNumber ->
                            viewModel.makePhoneCall(phoneNumber)
                        },
                        sendEmail = { email ->
                            viewModel.sendEmail(email)
                        },
                        openLocation = { lat, lng ->
                            viewModel.openLocation(lat, lng)
                        }
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("User not found")
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                UserDetailSideEffect.NavigateBack -> onNavigateBack()
                is UserDetailSideEffect.ShowError -> {
                    android.widget.Toast.makeText(
                        context,
                        effect.message,
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }

                is UserDetailSideEffect.MakePhoneCall -> {
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_DIAL,
                        android.net.Uri.parse("tel:${effect.phoneNumber}")
                    )
                    context.startActivity(intent)
                }

                is UserDetailSideEffect.SendEmail -> {
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_SENDTO,
                        android.net.Uri.parse("mailto:${effect.email}")
                    )
                    context.startActivity(intent)
                }

                is UserDetailSideEffect.OpenLocation -> {
                    val uri =
                        "geo:${effect.latitude},${effect.longitude}?q=${effect.latitude},${effect.longitude}"
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        android.net.Uri.parse(uri)
                    )
                    context.startActivity(intent)
                }
            }
        }
    }
}