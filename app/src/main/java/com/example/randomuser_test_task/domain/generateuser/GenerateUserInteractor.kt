package com.example.randomuser_test_task.domain.generateuser

import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.model.UserFilter

interface GenerateUserInteractor {
    suspend fun generateUsers(filter: UserFilter): Result<List<User>>
    suspend fun clearLocalUsers()
}