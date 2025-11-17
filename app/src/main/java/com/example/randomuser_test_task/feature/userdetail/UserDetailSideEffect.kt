package com.example.randomuser_test_task.feature.userdetail

sealed class UserDetailSideEffect {
    data object Finish : UserDetailSideEffect()
    data class MakePhoneCall(val phoneNumber: String) : UserDetailSideEffect()
    data class SendEmail(val email: String) : UserDetailSideEffect()
    data class OpenLocation(val latitude: Double, val longitude: Double) : UserDetailSideEffect()
}