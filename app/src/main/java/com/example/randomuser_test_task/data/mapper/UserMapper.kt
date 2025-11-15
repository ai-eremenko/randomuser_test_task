package com.example.randomuser_test_task.data.mapper

import com.example.randomuser_test_task.data.db.entity.UserEntity
import com.example.randomuser_test_task.data.dto.UserDto
import com.example.randomuser_test_task.data.dto.responses.LocationResponse
import com.example.randomuser_test_task.data.dto.responses.PictureResponse
import com.example.randomuser_test_task.data.dto.responses.UserResponse
import com.example.randomuser_test_task.domain.model.Picture
import com.example.randomuser_test_task.domain.model.User

object UserMapper {

    fun mapToDto(response: UserResponse): UserDto {
        return UserDto(
            id = response.login.uuid,
            gender = response.gender,
            title = response.name.title,
            firstName = response.name.first,
            lastName = response.name.last,
            email = response.email,
            phone = response.phone,
            cell = response.cell,
            picture = response.picture,
            nationality = response.nat,
            location = formatLocation(response.location),
            street = "${response.location.street.number} ${response.location.street.name}",
            city = response.location.city,
            state = response.location.state,
            country = response.location.country,
            age = response.dob.age,
            dateOfBirth = response.dob.date,
            registeredDate = response.registered.date,
            username = response.login.username
        )
    }

    fun UserDto.toUser(): User {
        return User(
            id = id,
            gender = gender,
            title = title,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            cell = cell,
            picture = picture.toPicture(),
            nationality = nationality,
            location = location,
            street = street,
            city = city,
            state = state,
            country = country,
            age = age,
            dateOfBirth = dateOfBirth,
            registeredDate = registeredDate,
            username = username
        )
    }

    private fun PictureResponse.toPicture(): Picture {
        return Picture(
            large = this.large,
            medium = this.medium,
            thumbnail = this.thumbnail
        )
    }

    fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            gender = gender,
            title = title,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            cell = cell,
            pictureLarge = picture.large,
            pictureMedium = picture.medium,
            pictureThumbnail = picture.thumbnail,
            nationality = nationality,
            location = location,
            street = street,
            city = city,
            state = state,
            country = country,
            age = age,
            dateOfBirth = dateOfBirth,
            registeredDate = registeredDate,
            username = username,
            createdAt = createdAt
        )
    }

    fun UserEntity.toUser(): User {
        return User(
            id = id,
            gender = gender,
            title = title,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            cell = cell,
            picture = Picture(
                large = pictureLarge,
                medium = pictureMedium,
                thumbnail = pictureThumbnail
            ),
            nationality = nationality,
            location = location,
            street = street,
            city = city,
            state = state,
            country = country,
            age = age,
            dateOfBirth = dateOfBirth,
            registeredDate = registeredDate,
            username = username,
            createdAt = createdAt
        )
    }

    fun UserDto.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            gender = gender,
            title = title,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            cell = cell,
            pictureLarge = picture.large,
            pictureMedium = picture.medium,
            pictureThumbnail = picture.thumbnail,
            nationality = nationality,
            location = location,
            street = street,
            city = city,
            state = state,
            country = country,
            age = age,
            dateOfBirth = dateOfBirth,
            registeredDate = registeredDate,
            username = username
        )
    }

    private fun formatLocation(location: LocationResponse): String {
        return with(location) {
            "$city, $state, $country"
        }
    }

    fun toUser(entity: UserEntity): User {
        return entity.toUser()
    }
}