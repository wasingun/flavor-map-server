package com.example.dto

import com.example.LocalDateTimeSerializer
import com.example.domain.model.User
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

class UserDto {
    @Serializable
    data class CreateUserRequest(
        val userId: String,
        val nickname: String,
        val email: String,
        val profileImage: String = "",
    )

    @Serializable
    data class GetUserResponse(
        val isSuccess: Boolean,
        val message: String,
        val user: User
    )

    @Serializable
    data class UpdateUserRequest(
        val userId: String,
        val nickname: String,
        val email: String,
        val profileImage: String = "",
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime,
        val primaryId: String,
    )
}
