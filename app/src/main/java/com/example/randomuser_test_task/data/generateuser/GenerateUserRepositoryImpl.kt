package com.example.randomuser_test_task.data.generateuser

import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.data.mapper.UserMapper.toEntity
import com.example.randomuser_test_task.data.mapper.UserMapper.toUser
import com.example.randomuser_test_task.data.network.UserApi
import com.example.randomuser_test_task.domain.generateuser.GenerateUserRepository
import com.example.randomuser_test_task.domain.model.Nationality
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.model.UserFilter

class GenerateUserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val mapper: UserMapper
) : GenerateUserRepository {

    override suspend fun generateUsers(filter: UserFilter): Result<List<User>> {
        return try {
            val nationalityParam = if (filter.nationalities.isNotEmpty()) {
                filter.nationalities.joinToString(",") { it.code.lowercase() }
            } else null

            val response = userApi.getRandomUsers(
                results = filter.resultsCount,
                gender = filter.gender?.apiValue?.takeIf { it.isNotEmpty() },
                nationality = nationalityParam
            )

            val users = response.results.map { mapper.mapToDto(it).toUser() }
            val entities = users.map { it.toEntity() }
            userDao.insertUsers(entities)

            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearLocalUsers() {
        userDao.clearAll()
    }
}