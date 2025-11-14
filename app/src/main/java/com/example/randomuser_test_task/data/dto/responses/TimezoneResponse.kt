package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class TimezoneResponse(
    @SerializedName("offset") val offset: String,
    @SerializedName("description") val description: String
)