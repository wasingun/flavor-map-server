package com.example.domain.model

import com.example.LocalDateTimeSerializer
import com.example.domain.BaseModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class User (
    val userId: String,
    val nickname: String,
    val email: String,
    val profileImage: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    override var primaryId: String? = null,
) : BaseModel