package com.example.randomuser_test_task.feature.generateuser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomuser_test_task.feature.generateuser.view.GenderSelection
import com.example.randomuser_test_task.feature.generateuser.view.NationalitySelection
import com.example.randomuser_test_task.uikit.BlueDark
import com.example.randomuser_test_task.uikit.BlueLight
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateUserScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUserList: () -> Unit,
    viewModel: GenerateUserViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Generate User",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BlueDark
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    enabled = state.selectedNationalities.isNotEmpty() && !state.isLoading,
                            colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.selectedNationalities.isNotEmpty() && !state.isLoading) {
                                BlueDark
                            } else {
                                BlueLight
                            },
                )
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Generate")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Select Gender",
                style = MaterialTheme.typography.titleMedium,
                color = BlueDark,
                modifier = Modifier.padding(bottom = 8.dp)

            )
            GenderSelection(
                selectedGender = state.selectedGender,
                onGenderSelected = { gender ->
                    viewModel.onEvent(GenerateUserEvent.OnGenderSelected(gender))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Select Nationality",
                style = MaterialTheme.typography.titleMedium,
                color = BlueDark,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            NationalitySelection(
                selectedNationalities = state.selectedNationalities,
                onNationalitiesChanged = { nationality ->
                    viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(nationality))
                }
            )
        }
    }

        LaunchedEffect(key1 = true) {
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    GenerateUserSideEffect.NavigateToUserList -> {
                        onNavigateToUserList()
                    }
                    is GenerateUserSideEffect.ShowError -> {
                    }
                }
            }
        }
}

