package com.example.randomuser_test_task.feature.userdetail

sealed class UserDetailSideEffect {
    data class ShowError(val message: String) : UserDetailSideEffect()
}