package com.example.randomuser_test_task.feature.generateuser.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.generateuser.GenerateUserEvent
import com.example.randomuser_test_task.feature.generateuser.GenerateUserState
import com.example.randomuser_test_task.uikit.BlueDark
import com.example.randomuser_test_task.uikit.BlueLight

@Composable
fun GenerateUserBottomBar(
    state: GenerateUserState,
    onEvent: (GenerateUserEvent) -> Unit
) {
    val buttonContainerColor = if (state.selectedNationalities.isNotEmpty() && !state.isLoading) {
        BlueDark
    } else {
        BlueLight
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            enabled = state.selectedNationalities.isNotEmpty() && !state.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = buttonContainerColor),
            onClick = { onEvent(GenerateUserEvent.OnGenerateButtonClicked) },
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White
                )
            } else {
                Text(stringResource(id = R.string.generate))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}