package com.example.randomuser_test_task.feature.generateuser

sealed class GenerateUserSideEffect {
    data class NavigateToUserList(val cameFromUserList: Boolean) : GenerateUserSideEffect()
    data object Finish : GenerateUserSideEffect()
}