package com.example.randomuser_test_task.feature.generateuser

sealed class GenerateUserSideEffect {
    data object NavigateToUserList : GenerateUserSideEffect()
    data class ShowError(val message: String) : GenerateUserSideEffect()
}