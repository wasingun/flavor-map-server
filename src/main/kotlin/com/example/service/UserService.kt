package com.example.service

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.dto.UserDto
import java.time.LocalDateTime

class UserService(
    private val repository: UserRepository
) {
    fun createUser(user: UserDto.CreateUserRequest) {
        repository.create(
            User(
                userId = user.userId,
                nickname = user.nickname,
                email = user.email,
                profileImage = user.profileImage,
                createdAt = LocalDateTime.now()
            )
        )
    }

    fun getUserByUserId(userId: String): UserDto.GetUserResponse {
        //TODO: 에러처리 별도 구현 예정
        val result = repository.findByUserId(userId)!!
        return UserDto.GetUserResponse(
            userId = result.userId,
            nickname = result.nickname,
            email = result.email,
            profileImage = result.profileImage,
        )
    }
}