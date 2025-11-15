package com.example.randomuser_test_task.feature.generateuser

import com.example.randomuser_test_task.domain.model.Gender
import com.example.randomuser_test_task.domain.model.Nationality

sealed class GenerateUserEvent {
    data class OnGenderSelected(val gender: Gender?) : GenerateUserEvent()
    data class OnNationalitySelected(val nationality: Nationality) : GenerateUserEvent()
    object OnGenerateButtonClicked : GenerateUserEvent()
}