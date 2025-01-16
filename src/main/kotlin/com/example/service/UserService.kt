package com.example.service

import com.example.ErrorCode
import com.example.GlobalException
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.dto.UserDto
import java.time.LocalDateTime
import java.util.*

class UserService(
    private val repository: UserRepository
) {
    fun createUser(user: UserDto.CreateUserRequest) {
        val existedUser = repository.findByUserId(user.userId)
        if (existedUser != null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_ALREADY_EXISTS,
                errorMessage = ErrorCode.USER_ALREADY_EXISTS.defaultMessage
            )
        }
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
        val existedUser = repository.findByUserId(userId)
            ?: throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        return UserDto.GetUserResponse(
            userId = existedUser.userId,
            nickname = existedUser.nickname,
            email = existedUser.email,
            profileImage = existedUser.profileImage,
            createdAt = existedUser.createdAt,
            primaryId = existedUser.primaryId!!
        )
    }

    fun updateUser(user: UserDto.UpdateUserRequest) {
        val existedUser = repository.findByUserId(user.userId)
        if (existedUser == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        }
        repository.update(
            User(
                userId = user.userId,
                nickname = user.nickname,
                email = user.email,
                profileImage = user.profileImage,
                createdAt = LocalDateTime.now(),
                primaryId = user.primaryId
            )
        )
    }

    fun deleteUser(primaryKey: String) {
        val primaryKeyUuid = UUID.fromString(primaryKey)
        val existedPrimaryKey = repository.read(primaryKeyUuid)
        if (existedPrimaryKey == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        }
        repository.delete(primaryKeyUuid)
    }
}