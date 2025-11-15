package com.example.randomuser_test_task.domain.model

data class User(
    val id: String,
    val gender: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: Picture,
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
    val username: String,
    val createdAt: Long = System.currentTimeMillis()
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)