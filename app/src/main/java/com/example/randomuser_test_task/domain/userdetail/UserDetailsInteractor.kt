package com.example.randomuser_test_task.domain.userdetail

import com.example.randomuser_test_task.domain.model.User

interface UserDetailsInteractor {
    suspend fun getUserById(id: String): User?
}