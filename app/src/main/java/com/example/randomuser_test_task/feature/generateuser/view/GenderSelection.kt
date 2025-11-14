package com.example.randomuser_test_task.feature.generateuser.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.model.Gender

@Composable
fun GenderSelection(
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Gender.entries.forEach { gender ->
            FilterChip(
                selected = selectedGender == gender,
                onClick = { onGenderSelected(gender) },
                label = { Text(gender.displayName) }
            )
        }
    }
}