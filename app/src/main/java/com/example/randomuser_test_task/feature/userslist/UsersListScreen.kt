package com.example.randomuser_test_task.feature.userslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.feature.userslist.view.UserCard
import com.example.randomuser_test_task.uikit.BlueDark
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUserDetail: (User) -> Unit,
    viewModel: UsersListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagingItems = viewModel.usersPagingFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Users List",
                        fontWeight = FontWeight.Bold
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = BlueDark
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.isEmpty -> {
                EmptyUsersState(modifier = Modifier.padding(paddingValues))
            }
            else -> {
                UsersListContent(
                    pagingItems = pagingItems,
                    onUserClick = { user ->
                        viewModel.onEvent(UsersListEvent.onUserClicked(user))
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
        LaunchedEffect(key1 = true) {
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is UsersListSideEffect.NavigateToUserDetail ->
                        onNavigateToUserDetail(sideEffect.user)
                    is UsersListSideEffect.ShowError -> {
                    }
                }
            }
        }
    }
}

@Composable
private fun UsersListContent(
    pagingItems: LazyPagingItems<User>,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        pagingItems.loadState.refresh is LoadState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        pagingItems.loadState.refresh is LoadState.Error -> {
            val error = pagingItems.loadState.refresh as LoadState.Error
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error loading users: ${error.error.message}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { pagingItems.retry() }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
        pagingItems.itemCount == 0 -> {
            EmptyUsersState(modifier = modifier)
        }
        else -> {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = { index ->
                        pagingItems[index]?.id ?: index
                    }
                ) { index ->
                    val user = pagingItems[index]
                    if (user != null) {
                        UserCard(
                            user = user,
                            onUserClick = { onUserClick(user) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (pagingItems.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (pagingItems.loadState.append is LoadState.Error) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Error loading more users",
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { pagingItems.retry() }
                                ) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyUsersState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No users found",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}