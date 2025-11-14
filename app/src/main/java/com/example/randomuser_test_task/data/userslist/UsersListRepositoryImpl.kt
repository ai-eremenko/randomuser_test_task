package com.example.randomuser_test_task.data.userslist

import androidx.paging.PagingSource
import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.userslist.UsersListRepository

class UsersListRepositoryImpl(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : UsersListRepository {

    override fun getUsersPagingSource(): PagingSource<Int, User> {
        return userDao.getUsersPagingSource().map { entity -> entity.toUser() }
    }

    override suspend fun getUsersCount(): Int {
        return userDao.getUsersCount()
    }
}