package com.example.randomuser_test_task.domain.userslist

import androidx.paging.PagingSource
import com.example.randomuser_test_task.domain.model.User

interface UsersListRepository {
    fun getUsersPagingSource(): PagingSource<Int, User>
    suspend fun getUsersCount(): Int
}