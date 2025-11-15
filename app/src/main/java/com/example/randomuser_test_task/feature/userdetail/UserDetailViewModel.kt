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
            UserDetailEvent.OnLoadUser -> loadUser()
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.emit(UserDetailSideEffect.NavigateBack)
        }
    }

    fun makePhoneCall(phoneNumber: String) {
        viewModelScope.launch {
            _sideEffect.emit(UserDetailSideEffect.MakePhoneCall(phoneNumber))
        }
    }

    fun sendEmail(email: String) {
        viewModelScope.launch {
            _sideEffect.emit(UserDetailSideEffect.SendEmail(email))
        }
    }

    fun openLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _sideEffect.emit(UserDetailSideEffect.OpenLocation(latitude, longitude))
        }
    }

    fun onTabSelected(tabIndex: Int) {
        _state.update { it.copy(selectedTabIndex = tabIndex) }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

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
                        isLoading = false
                    )
                }
                _sideEffect.emit(UserDetailSideEffect.ShowError(e.message ?: "Failed to load user details"))
            }
        }
    }
}