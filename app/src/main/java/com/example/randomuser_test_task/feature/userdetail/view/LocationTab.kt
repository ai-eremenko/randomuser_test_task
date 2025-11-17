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
fun LocationTab(
    user: User,
    onEvent: (UserDetailEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        InfoRow(stringResource(id = R.string.street_label), user.street)
        InfoRow(stringResource(id = R.string.city_label), user.city)
        InfoRow(stringResource(id = R.string.state_label), user.state)
        InfoRow(stringResource(id = R.string.country_label), user.country)
        InfoRow(stringResource(id = R.string.location_label), user.location)
        ClickableInfoRow(
            label = stringResource(id = R.string.coordinates_label),
            value = "${user.latitude}, ${user.longitude}",
            onClick = { onEvent(UserDetailEvent.OnLocationClicked) }
        )
    }
}