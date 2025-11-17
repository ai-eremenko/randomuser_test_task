package com.example.randomuser_test_task.feature.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser_test_task.data.repository.UsersRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val usersRepository: UsersRepository,
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
            UserDetailEvent.OnBackButtonClicked -> onBackButtonClicked()
            is UserDetailEvent.OnTabSelected -> onTabSelected(event.tabIndex)
            is UserDetailEvent.OnPhoneClicked -> makePhoneCall()
            is UserDetailEvent.OnEmailClicked -> sendEmail()
            is UserDetailEvent.OnLocationClicked -> openLocation()
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _sideEffect.emit(UserDetailSideEffect.Finish)
        }
    }

    private fun makePhoneCall() {
        viewModelScope.launch {
            val phoneNumber = state.value.user?.phone ?: return@launch
            if (phoneNumber.isBlank()) return@launch
            _sideEffect.emit(UserDetailSideEffect.MakePhoneCall(phoneNumber))
        }
    }

    private fun sendEmail() {
        viewModelScope.launch {
            val email = state.value.user?.email ?: return@launch
            if (email.isBlank()) return@launch
            _sideEffect.emit(UserDetailSideEffect.SendEmail(email))
        }
    }

    private fun openLocation() {
        viewModelScope.launch {
            val latitude = state.value.user?.latitude ?: return@launch
            val longitude = state.value.user?.longitude ?: return@launch
            _sideEffect.emit(UserDetailSideEffect.OpenLocation(latitude, longitude))
        }
    }

    private fun onTabSelected(tabIndex: Int) {
        _state.update { it.copy(selectedTabIndex = tabIndex) }
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = usersRepository.getUserById(userId)
            _state.update { it.copy(user = user) }
        }
    }
}