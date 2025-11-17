@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.randomuser_test_task.feature.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.randomuser_test_task.R
import com.example.randomuser_test_task.feature.userdetail.view.UserTabsSection
import com.example.randomuser_test_task.uikit.BlueDark

@Composable
fun UserDetailScreenView(state: UserDetailState, onEvent: (UserDetailEvent) -> Unit) {
    Scaffold(
        containerColor = BlueDark,
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(UserDetailEvent.OnBackButtonClicked) },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White, CircleShape)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = BlueDark
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BlueDark,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (state.user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(BlueDark)
                )
                Column(
                    modifier = Modifier.offset(y = (-50).dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = state.user.picture.large,
                        contentDescription = stringResource(id = R.string.user_photo),
                        modifier = Modifier

                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = stringResource(id = R.string.hi_how_are_you),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = stringResource(id = R.string.im),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${state.user.firstName} ${state.user.lastName}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    UserTabsSection(
                        modifier = Modifier.padding(top = 24.dp),
                        user = state.user,
                        selectedTabIndex = state.selectedTabIndex,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}