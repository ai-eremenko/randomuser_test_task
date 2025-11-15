package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.model.User

sealed class UsersListEvent {
    data class onUserClicked(val user: User) : UsersListEvent()
}