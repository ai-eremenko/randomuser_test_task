package com.example.randomuser_test_task.feature.userdetail.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.feature.userdetail.ClickableInfoRow
import com.example.randomuser_test_task.feature.userdetail.UserDetailSideEffect
import com.example.randomuser_test_task.feature.userdetail.view.InfoRow

@Composable
fun PhoneTab(
    user: User,
    makePhoneCall: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ClickableInfoRow(
            label = "Phone:",
            value = user.phone,
            onClick = { makePhoneCall(user.phone) }
        )
        ClickableInfoRow(
            label = "Cell:",
            value = user.cell,
            onClick = { makePhoneCall(user.cell) }
        )
    }
}