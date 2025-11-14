package com.example.randomuser_test_task.data.dto.responses

import com.google.gson.annotations.SerializedName

data class PictureResponse(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("thumbnail") val thumbnail: String
)