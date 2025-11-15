package com.example.randomuser_test_task.data.dto

import com.example.randomuser_test_task.data.dto.responses.PictureResponse

data class UserDto(
    val id: String,
    val gender: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: PictureResponse,
    val nationality: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val age: Int,
    val dateOfBirth: String,
    val registeredDate: String,
    val username: String
)