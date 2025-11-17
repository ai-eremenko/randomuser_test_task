package com.example.randomuser_test_task.data.repository

import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.domain.UserFilter

interface UsersRepository {
    suspend fun generateUser(filter: UserFilter): Result<Unit>
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User?
}