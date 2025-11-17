package com.example.randomuser_test_task.data.repository

import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.data.mapper.UserMapper.toEntity
import com.example.randomuser_test_task.data.mapper.UserMapper.toUser
import com.example.randomuser_test_task.data.network.UserApi
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.domain.UserFilter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UsersRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val mapper: UserMapper,
    private val dispatcher: CoroutineDispatcher
) : UsersRepository {

    override suspend fun generateUser(filter: UserFilter): Result<Unit> {
        return withContext(dispatcher) {
            try {
                val nationalityParam = if (filter.nationalities.isNotEmpty()) {
                    filter.nationalities.joinToString(",") { it.code.lowercase() }
                } else null
                val response = userApi.getRandomUsers(
                    results = filter.resultsCount,
                    gender = filter.gender?.apiValue?.takeIf { it.isNotEmpty() },
                    nationality = nationalityParam
                )
                val users = response.results.map { mapper.mapToDto(it).toUser() }
                val firstUser = users.firstOrNull()
                if (firstUser == null) {
                    return@withContext Result.failure(Exception("Generation user error"))
                }
                userDao.insertUser(firstUser.toEntity())
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getUsers(): List<User> {
        return withContext(dispatcher) {
            userDao.getUsers().map { it.toUser() }
        }
    }

    override suspend fun getUserById(userId: String): User {
        return withContext(dispatcher) {
            userDao.getUserById(id = userId).toUser()
        }
    }
}