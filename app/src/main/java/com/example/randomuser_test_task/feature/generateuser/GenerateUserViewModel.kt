package com.example.randomuser_test_task.feature.generateuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.data.repository.UsersRepository
import com.example.randomuser_test_task.domain.Gender
import com.example.randomuser_test_task.domain.Nationality
import com.example.randomuser_test_task.domain.UserFilter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GenerateUserViewModel(
    private val usersRepository: UsersRepository,
    private val cameFromUserList: Boolean
) : ViewModel() {

    private val _state = MutableStateFlow(
        GenerateUserState(isCameFromList = cameFromUserList)
    )
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<GenerateUserSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: GenerateUserEvent) {
        when (event) {
            is GenerateUserEvent.OnGenderSelected -> onGenderSelected(event.gender)
            is GenerateUserEvent.OnNationalitySelected -> onNationalitySelected(event.nationality)
            is GenerateUserEvent.OnGenerateButtonClicked -> onGenerateButtonClicked()
            GenerateUserEvent.OnBackButtonClicked -> onBackButtonClicked()
            GenerateUserEvent.OnDialogOkButtonClicked -> onDialogOkButtonClicked()
        }
    }

    private fun onDialogOkButtonClicked() {
        viewModelScope.launch {
            _sideEffect.emit(GenerateUserSideEffect.NavigateToUserList(cameFromUserList))
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _sideEffect.emit(GenerateUserSideEffect.Finish)
        }
    }

    private fun onGenderSelected(gender: Gender?) {
        val newGender = if (_state.value.selectedGender == gender) {
            null
        } else {
            gender
        }
        _state.update { it.copy(selectedGender = newGender) }
    }

    private fun onNationalitySelected(nationality: Nationality) {
        val currentNationalities = _state.value.selectedNationalities.toMutableSet()
        if (currentNationalities.contains(nationality)) {
            currentNationalities.remove(nationality)
        } else {
            currentNationalities.add(nationality)
        }
        _state.update { it.copy(selectedNationalities = currentNationalities) }
    }

    private fun onGenerateButtonClicked() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = usersRepository.generateUser(
                UserFilter(
                    gender = _state.value.selectedGender,
                    nationalities = _state.value.selectedNationalities,
                    resultsCount = 20
                )
            )
            _state.update { it.copy(isLoading = false) }
            result.fold(
                onSuccess = {
                    _sideEffect.emit(GenerateUserSideEffect.NavigateToUserList(cameFromUserList))
                },
                onFailure = { error ->
                    _state.update { it.copy(isErrorDialogShown = true) }
                }
            )
        }
    }
}