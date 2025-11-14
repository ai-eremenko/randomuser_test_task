package com.example.randomuser_test_task.data.userdetail

import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.data.mapper.UserMapper.toUser
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.userdetail.UserDetailsRepository

class UserDetailsRepositoryImpl(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : UserDetailsRepository {

    override suspend fun getUserById(id: String): User? {
        return userDao.getUserById(id)?.toUser()
    }
}