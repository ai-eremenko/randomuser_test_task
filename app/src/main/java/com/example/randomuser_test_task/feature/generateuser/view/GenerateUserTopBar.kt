package com.example.randomuser_test_task.feature.generateuser.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.generateuser.GenerateUserEvent
import com.example.randomuser_test_task.uikit.BlueDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateUserTopBar(
    showBackButton: Boolean = false,
    onEvent: (GenerateUserEvent) -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.generate_user),
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onEvent(GenerateUserEvent.OnBackButtonClicked) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = BlueDark
                    )
                }
            }
        }
    )
}