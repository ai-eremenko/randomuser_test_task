package com.example.randomuser_test_task.data.userslist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.randomuser_test_task.data.db.dao.UserDao
import com.example.randomuser_test_task.data.mapper.UserMapper
import com.example.randomuser_test_task.data.mapper.UserMapper.toUser
import com.example.randomuser_test_task.domain.model.User

class UserPagingSource(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val offset = page * pageSize

            val users = userDao.getUsersPaginated(limit = pageSize, offset = offset)
                .map { userEntity -> userEntity.toUser() }

            LoadResult.Page(
                data = users,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (users.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

