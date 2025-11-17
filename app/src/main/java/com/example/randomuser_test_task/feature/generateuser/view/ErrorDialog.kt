package com.example.randomuser_test_task.feature.generateuser.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.generateuser.GenerateUserEvent
import com.example.randomuser_test_task.feature.generateuser.GenerateUserState
import com.example.randomuser_test_task.uikit.BlueDark

@Composable
fun ErrorDialog(
    state: GenerateUserState,
    onEvent: (GenerateUserEvent) -> Unit
) {
    if (state.isErrorDialogShown) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp),
            ) {
                Text(stringResource(id = R.string.dialog_error), textAlign = TextAlign.Center)
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BlueDark),
                    onClick = { onEvent(GenerateUserEvent.OnDialogOkButtonClicked) }
                ) {
                    Text(stringResource(id = android.R.string.ok))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}