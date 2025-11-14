package com.example.randomuser_test_task.domain.userslist

import androidx.paging.PagingSource
import com.example.randomuser_test_task.domain.model.User

class UsersListInteractorImpl(
    private val repository: UsersListRepository
) : UsersListInteractor {

    override fun getUsersPagingSource(): PagingSource<Int, User> {
        return repository.getUsersPagingSource()
    }

    override suspend fun getUsersCount(): Int {
        return repository.getUsersCount()
    }
}