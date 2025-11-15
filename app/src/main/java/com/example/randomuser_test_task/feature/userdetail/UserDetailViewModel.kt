package com.example.randomuser_test_task.feature.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.domain.userdetail.UserDetailsInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val interactor: UserDetailsInteractor,
    private val userId: String
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UserDetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        loadUser()
    }

    fun onEvent(event: UserDetailEvent) {
        when (event) {
            UserDetailEvent.LoadUser -> loadUser()
            UserDetailEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val user = interactor.getUserById(userId)
                _state.update {
                    it.copy(
                        user = user,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load user details"
                    )
                }
            }
        }
    }
}