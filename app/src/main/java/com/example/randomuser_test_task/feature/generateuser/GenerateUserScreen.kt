package com.example.randomuser_test_task.feature.generateuser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.randomuser_test_task.domain.model.Gender
import com.example.randomuser_test_task.domain.model.Nationality
import com.example.randomuser_test_task.feature.generateuser.view.GenderSelection
import com.example.randomuser_test_task.feature.generateuser.view.NationalitySelection
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = state.selectedNationalities.isNotEmpty() && !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary
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
                style = MaterialTheme.typography.headlineSmall,
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
                style = MaterialTheme.typography.headlineSmall,
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
                    GenerateUserSideEffect.NavigateToUserList -> onNavigateToUserList()
                    is GenerateUserSideEffect.ShowError -> {
                    }
                }
            }
        }
}

