package com.example.randomuser_test_task.domain.userdetail

import com.example.randomuser_test_task.domain.model.User

class UserDetailsInteractorImpl(
    private val repository: UserDetailsRepository
) : UserDetailsInteractor {

    override suspend fun getUserById(id: String): User? {
        return repository.getUserById(id)
    }
}