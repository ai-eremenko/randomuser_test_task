package com.example.randomuser_test_task.feature.userdetail.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.userdetail.UserDetailEvent

@Composable
fun PhoneTab(
    user: User,
    onEvent: (UserDetailEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ClickableInfoRow(
            label = stringResource(id = R.string.phone_label),
            value = user.phone,
            onClick = { onEvent(UserDetailEvent.OnPhoneClicked) }
        )
    }
}