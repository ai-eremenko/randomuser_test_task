package com.example.randomuser_test_task.feature.userdetail

sealed class UserDetailEvent {
    object LoadUser : UserDetailEvent()
    object ErrorShown : UserDetailEvent()
}