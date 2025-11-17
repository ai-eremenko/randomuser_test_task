package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.User

sealed class UsersListSideEffect {
    data class NavigateToUserDetail(val user: User) : UsersListSideEffect()
    object NavigateToGenerateUser : UsersListSideEffect()
}