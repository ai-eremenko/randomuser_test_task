package com.example.randomuser_test_task.data.userslist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.domain.model.User
import com.example.randomuser_test_task.domain.userslist.UsersListRepository

class UsersListRepositoryImpl(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : UsersListRepository {

    override fun getUsersPagingSource(): PagingSource<Int, User> {
        return UserPagingSource(userDao, mapper)
    }

    override suspend fun getUsersCount(): Int {
        return userDao.getUsersCount()
    }
}

class UserPagingSource(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val offset = page * params.loadSize

            val users = userDao.getUsersPaginated(limit = params.loadSize, offset = offset)

            LoadResult.Page(
                data = users.map { mapper.toUser(it) },
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (users.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}