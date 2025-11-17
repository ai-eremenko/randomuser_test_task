package com.example.randomuser_test_task.data.network

import com.example.randomuser_test_task.data.dto.responses.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api/")
    suspend fun getRandomUsers(
        @Query("results") results: Int = 1,
        @Query("gender") gender: String? = null,
        @Query("nat") nationality: String? = null,
        @Query("inc") includeFields: String? = null,
    ): ApiResponse
}
