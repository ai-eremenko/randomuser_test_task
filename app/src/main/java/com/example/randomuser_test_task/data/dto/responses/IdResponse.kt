package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class IdResponse(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: String?
)