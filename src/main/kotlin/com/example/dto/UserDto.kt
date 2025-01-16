package com.example.dto

import com.example.LocalDateTimeSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

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
        val userId: String,
        val nickname: String,
        val email: String,
        val profileImage: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime,
        @Contextual val primaryId: UUID,
    )

    @Serializable
    data class UpdateUserRequest(
        val userId: String,
        val nickname: String,
        val email: String,
        val profileImage: String = "",
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime,
        @Contextual val primaryId: UUID,
    )

    @Serializable
    data class DeleteUserRequest(
        @Contextual val primaryId: UUID,
    )
}
