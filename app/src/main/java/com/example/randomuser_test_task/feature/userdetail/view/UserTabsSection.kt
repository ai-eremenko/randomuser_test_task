package com.example.randomuser_test_task.feature.userdetail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.uikit.BlueDark
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.userdetail.UserDetailEvent

@Composable
fun UserTabsSection(
    modifier: Modifier = Modifier,
    user: User,
    selectedTabIndex: Int,
    onEvent: (UserDetailEvent) -> Unit
) {
    val tabs = listOf(
        TabItem(Icons.Default.Person, stringResource(id = R.string.tab_info)),
        TabItem(Icons.Default.Phone, stringResource(id = R.string.tab_phone)),
        TabItem(Icons.Default.Email, stringResource(id = R.string.tab_email)),
        TabItem(Icons.Default.Place, stringResource(id = R.string.tab_location))
    )

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = BlueDark,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    height = 2.dp,
                    color = BlueDark
                )
            }
        ) {
            tabs.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onEvent(UserDetailEvent.OnTabSelected(index)) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selectedTabIndex == index) BlueDark else Color.Gray
                        )
                    }
                )
            }
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            when (selectedTabIndex) {
                0 -> PersonalInfoTab(user)
                1 -> PhoneTab(user = user, onEvent = onEvent)
                2 -> EmailTab(user = user, onEvent = onEvent)
                3 -> LocationTab(user = user, onEvent)
            }
        }
    }
}

data class TabItem(
    val icon: ImageVector,
    val title: String
)