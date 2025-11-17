package com.example.randomuser_test_task.feature.generateuser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.generateuser.view.ErrorDialog
import com.example.randomuser_test_task.feature.generateuser.view.GenderSelection
import com.example.randomuser_test_task.feature.generateuser.view.GenerateUserBottomBar
import com.example.randomuser_test_task.feature.generateuser.view.GenerateUserTopBar
import com.example.randomuser_test_task.feature.generateuser.view.NationalitySelection
import com.example.randomuser_test_task.uikit.BlueDark

@Composable
fun GenerateUserScreenView(state: GenerateUserState, onEvent: (GenerateUserEvent) -> Unit){
    Scaffold(
        topBar = {
            GenerateUserTopBar(
                showBackButton = state.isCameFromList,
                onEvent = onEvent
            )
        },
        bottomBar = {
            GenerateUserBottomBar(
                state = state,
                onEvent = onEvent
            )
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
                text = stringResource(id = R.string.select_gender),
                style = MaterialTheme.typography.titleMedium,
                color = BlueDark,
                modifier = Modifier.padding(bottom = 8.dp)

            )
            GenderSelection(
                selectedGender = state.selectedGender,
                availableGenders = state.availableGenders,
                onEvent = onEvent
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.select_nationality),
                style = MaterialTheme.typography.titleMedium,
                color = BlueDark,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            NationalitySelection(
                selectedNationalities = state.selectedNationalities,
                onEvent = onEvent
            )
        }
    }
    ErrorDialog(state = state, onEvent = onEvent)
}