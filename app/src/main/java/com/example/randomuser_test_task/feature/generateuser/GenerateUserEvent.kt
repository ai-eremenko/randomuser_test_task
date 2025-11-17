package com.example.randomuser_test_task.feature.generateuser

import com.example.randomuser_test_task.domain.Gender
import com.example.randomuser_test_task.domain.Nationality

sealed class GenerateUserEvent {
    data class OnGenderSelected(val gender: Gender?) : GenerateUserEvent()
    data class OnNationalitySelected(val nationality: Nationality) : GenerateUserEvent()
    data object OnGenerateButtonClicked : GenerateUserEvent()
    data object OnBackButtonClicked : GenerateUserEvent()
    data object OnDialogOkButtonClicked : GenerateUserEvent()
}