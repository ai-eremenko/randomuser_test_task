package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.model.User

data class UsersListState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false
)