package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results") val results: List<UserResponse>,
    @SerializedName("info") val info: InfoResponse
)