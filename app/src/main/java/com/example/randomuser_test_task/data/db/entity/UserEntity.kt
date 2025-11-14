package com.example.randomuser_test_task.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val gender: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val cell: String,
    val pictureLarge: String,
    val pictureMedium: String,
    val pictureThumbnail: String,
    val nationality: String,
    val location: String,
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