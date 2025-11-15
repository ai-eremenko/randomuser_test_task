package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.model.User

sealed class UsersListEvent {
    object LoadUsers : UsersListEvent()
    data class UserClicked(val user: User) : UsersListEvent()
    object ErrorShown : UsersListEvent()
    object Refresh : UsersListEvent()
}