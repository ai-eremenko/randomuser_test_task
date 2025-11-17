package com.example.randomuser_test_task.feature.userslist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CountryInfo(
    modifier: Modifier = Modifier,
    countryCode: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getFlagEmoji(countryCode),
                style = MaterialTheme.typography.bodySmall
            )
        }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = countryCode,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }

private fun getFlagEmoji(countryCode: String): String {
    if (countryCode.length != 2) return ""
    val firstChar = 0x1F1E6 + (countryCode[0].uppercaseChar() - 'A')
    val secondChar = 0x1F1E6 + (countryCode[1].uppercaseChar() - 'A')

    return String(Character.toChars(firstChar)) +
                String(Character.toChars(secondChar))
}