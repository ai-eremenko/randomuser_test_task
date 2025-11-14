package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("seed") val seed: String,
    @SerializedName("results") val results: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("version") val version: String
)