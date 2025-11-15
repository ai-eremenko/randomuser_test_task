package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.model.User

sealed class UsersListSideEffect {
    data class NavigateToUserDetail(val user: User) : UsersListSideEffect()
    data class ShowError(val message: String) : UsersListSideEffect()
}