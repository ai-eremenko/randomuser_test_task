package com.example.randomuser_test_task.feature.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.domain.userslist.UsersListInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val interactor: UsersListInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(UsersListState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UsersListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        loadUsers()
    }

    fun onEvent(event: UsersListEvent) {
        when (event) {
            is UsersListEvent.onUserClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(UsersListSideEffect.NavigateToUserDetail(event.user))
                }
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val usersCount = interactor.getUsersCount()
                val isEmpty = usersCount == 0

                _state.update {
                    it.copy(
                        isLoading = false,
                        isEmpty = isEmpty
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _sideEffect.emit(UsersListSideEffect.ShowError(e.message ?: "Failed to load users"))
            }
        }
    }
}