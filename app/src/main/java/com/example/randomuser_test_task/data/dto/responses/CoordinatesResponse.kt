package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class CoordinatesResponse(
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)