package com.example.randomuser_test_task.feature.userdetail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.feature.userdetail.view.*

@Composable
fun UserTabsSection(
    user: User,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    makePhoneCall: (String) -> Unit,
    sendEmail: (String) -> Unit,
    openLocation: (Double, Double) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabItem(Icons.Default.Person, "Info"),
        TabItem(Icons.Default.Phone, "Phone"),
        TabItem(Icons.Default.Email, "Email"),
        TabItem(Icons.Default.Place, "Location")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PersonalInfoTab(user)
            1 -> PhoneTab(user, makePhoneCall)
            2 -> EmailTab(user, sendEmail)
            3 -> LocationTab(user, openLocation)
        }
    }
}

data class TabItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String
)