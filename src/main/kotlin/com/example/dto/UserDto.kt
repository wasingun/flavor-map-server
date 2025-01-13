package com.example.dto

import kotlinx.serialization.Serializable

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
    )
}
