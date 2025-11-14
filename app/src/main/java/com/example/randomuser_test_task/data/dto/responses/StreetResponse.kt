package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class StreetResponse(
    @SerializedName("number") val number: Int,
    @SerializedName("name") val name: String
)