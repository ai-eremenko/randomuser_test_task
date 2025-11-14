package com.example.randomuser_test_task.domain.generateuser

import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.model.UserFilter

class GenerateUserInteractorImpl(
    private val repository: GenerateUserRepository
) : GenerateUserInteractor {
    override suspend fun generateUsers(filter: UserFilter): Result<List<User>>{
        return repository.generateUsers(filter)
    }
    override suspend fun clearLocalUsers() {
        repository.clearLocalUsers()
    }
}


