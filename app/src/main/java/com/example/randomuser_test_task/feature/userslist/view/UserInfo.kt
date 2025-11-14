package com.example.randomuser_test_task.feature.userslist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.model.User

@Composable
fun UserInfo(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.phone,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))
        CountryInfo(
            countryCode = user.nationality,
            modifier = Modifier.fillMaxWidth()
        )
    }
}