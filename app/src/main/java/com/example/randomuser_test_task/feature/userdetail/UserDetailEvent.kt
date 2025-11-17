package com.example.randomuser_test_task.feature.userdetail

sealed class UserDetailEvent {
    data object OnBackButtonClicked : UserDetailEvent()
    data class OnTabSelected(val tabIndex: Int) : UserDetailEvent()
    data object OnPhoneClicked : UserDetailEvent()
    data object OnEmailClicked : UserDetailEvent()
    data object OnLocationClicked : UserDetailEvent()
}