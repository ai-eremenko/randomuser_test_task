package com.example.randomuser_test_task.feature.userdetail

import com.example.randomuser_test_task.domain.model.User

data class UserDetailState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0
)