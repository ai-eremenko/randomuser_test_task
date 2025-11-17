package com.example.randomuser_test_task.feature.userdetail.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.R

@Composable
fun PersonalInfoTab(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        InfoRow(stringResource(id = R.string.first_name_label), user.firstName)
        InfoRow(stringResource(id = R.string.last_name_label), user.lastName)
        InfoRow(stringResource(id = R.string.gender_label), user.gender)
        InfoRow(stringResource(id = R.string.age_label), user.age.toString())
        InfoRow(stringResource(id = R.string.date_of_birth_label), user.dateOfBirth)
    }
}