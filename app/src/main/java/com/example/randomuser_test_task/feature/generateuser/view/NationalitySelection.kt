package com.example.randomuser_test_task.feature.generateuser.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.model.Nationality

@Composable
fun NationalitySelection(
    selectedNationalities: Set<Nationality>,
    onNationalitiesChanged: (Set<Nationality>) -> Unit
) {
    Column {
        Nationality.entries.forEach { nationality ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedNationalities.contains(nationality),
                    onCheckedChange = { checked ->
                        val newSelection = selectedNationalities.toMutableSet()
                        if (checked) {
                            newSelection.add(nationality)
                        } else {
                            newSelection.remove(nationality)
                        }
                        onNationalitiesChanged(newSelection)
                    }
                )
                Text(
                    text = nationality.displayName,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}