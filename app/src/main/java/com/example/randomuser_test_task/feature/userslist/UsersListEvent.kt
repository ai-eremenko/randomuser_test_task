package com.example.randomuser_test_task.feature.userslist

import com.example.randomuser_test_task.domain.User

sealed class UsersListEvent {
    data object OnFetchData : UsersListEvent()
    data class OnUserClicked(val user: User) : UsersListEvent()
    object OnAddButtonClicked : UsersListEvent()
}