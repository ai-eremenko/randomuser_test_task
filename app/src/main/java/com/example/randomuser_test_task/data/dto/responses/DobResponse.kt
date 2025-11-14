package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class DobResponse(
    @SerializedName("date") val date: String,
    @SerializedName("age") val age: Int
)