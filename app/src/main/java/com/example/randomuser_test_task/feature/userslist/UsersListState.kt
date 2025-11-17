package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.User

data class UsersListState(
    val listUsers: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false
)