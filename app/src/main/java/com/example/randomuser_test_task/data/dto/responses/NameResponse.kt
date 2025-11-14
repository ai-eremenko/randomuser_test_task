package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class NameResponse(
    @SerializedName("title") val title: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)