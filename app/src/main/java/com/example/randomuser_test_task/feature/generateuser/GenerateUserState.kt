package com.example.randomuser_test_task.feature.generateuser

import com.example.randomuser_test_task.domain.Gender
import com.example.randomuser_test_task.domain.Nationality

data class GenerateUserState(
    val isCameFromList: Boolean = false,
    val selectedGender: Gender? = null,
    val selectedNationalities: Set<Nationality> = emptySet(),
    val isLoading: Boolean = false,
    val availableGenders: List<Gender> = Gender.entries,
    val isErrorDialogShown: Boolean = false
)