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
fun LocationTab(
    user: User,
    openLocation: (Double, Double) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        InfoRow("Street:", user.street)
        InfoRow("City:", user.city)
        InfoRow("State:", user.state)
        InfoRow("Country:", user.country)

        ClickableInfoRow(
            label = "Coordinates:",
            value = "${user.latitude}, ${user.longitude}",
            onClick = { openLocation(user.latitude, user.longitude) }
        )
        InfoRow("Location:", user.location)
    }
}