package com.example.randomuser_test_task.feature.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.data.repository.UsersRepository
import com.example.randomuser_test_task.domain.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val usersRepository: UsersRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UsersListState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<UsersListSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: UsersListEvent) {
        when (event) {
            is UsersListEvent.OnUserClicked -> onUserClicked(event.user)
            is UsersListEvent.OnAddButtonClicked -> onAddButtonClicked()
            UsersListEvent.OnFetchData -> onFetchData()
        }
    }

    private fun onFetchData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val users = usersRepository.getUsers()
            if (users.isNotEmpty()) {
                _state.update { it.copy(listUsers = users, isEmpty = false) }
            } else {
                _state.update { it.copy(isEmpty = true) }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun onAddButtonClicked() {
        viewModelScope.launch {
            _sideEffect.emit(UsersListSideEffect.NavigateToGenerateUser)
        }
    }

    private fun onUserClicked(user: User) {
        viewModelScope.launch {
            _sideEffect.emit(UsersListSideEffect.NavigateToUserDetail(user))
        }
    }
}