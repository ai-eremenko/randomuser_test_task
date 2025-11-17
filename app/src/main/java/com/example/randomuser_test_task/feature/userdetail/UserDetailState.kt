package com.example.randomuser_test_task.feature.userdetail

import com.example.randomuser_test_task.domain.User

data class UserDetailState(
    val user: User? = null,
    val selectedTabIndex: Int = 0
)