package com.example.dto

import kotlinx.serialization.Serializable

class BaseDto {
    @Serializable
    data class BaseResponse(
        val success: Boolean,
        val message: String
    )
}