package com.example.randomuser_test_task.feature.generateuser

import com.example.randomuser_test_task.domain.model.Gender
import com.example.randomuser_test_task.domain.model.Nationality

data class GenerateUserState(
    val selectedGender: Gender? = null,
    val selectedNationalities: Set<Nationality> = emptySet(),
    val isLoading: Boolean = false
)