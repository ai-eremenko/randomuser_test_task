package com.example.randomuser_test_task.feature.generateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.domain.generateuser.GenerateUserInteractor
import com.example.randomuser_test_task.domain.model.Gender
import com.example.randomuser_test_task.domain.model.Nationality
import com.example.randomuser_test_task.domain.model.UserFilter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateUserViewModel(
    private val interactor: GenerateUserInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(GenerateUserState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<GenerateUserSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: GenerateUserEvent) {
        when (event) {
            is GenerateUserEvent.OnGenderSelected -> {
                _state.update { it.copy(selectedGender = event.gender) }
            }
            is GenerateUserEvent.OnNationalitySelected -> {
                val currentNationalities = _state.value.selectedNationalities.toMutableSet()
                if (currentNationalities.contains(event.nationality)) {
                    currentNationalities.remove(event.nationality)
                } else {
                    currentNationalities.add(event.nationality)
                }
                _state.update { it.copy(selectedNationalities = currentNationalities) }
            }
            GenerateUserEvent.OnGenerateButtonClicked -> {
                generateUsers()
            }
        }
    }

    private fun generateUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = interactor.generateUsers(
                UserFilter(
                    gender = _state.value.selectedGender,
                    nationalities = _state.value.selectedNationalities,
                    resultsCount = 20
                )
            )

            _state.update { it.copy(isLoading = false) }

            result.fold(
                onSuccess = {
                    _sideEffect.emit(GenerateUserSideEffect.NavigateToUserList)
                },
                onFailure = { error ->
                    _sideEffect.emit(
                        GenerateUserSideEffect.ShowError(
                            message = error.message ?: "Unknown error"
                        )
                    )
                }
            )
        }
    }
}