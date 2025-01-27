package com.example.domain.model

import com.example.LocalDateTimeSerializer
import com.example.domain.BaseModel
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Review (
    val writerId: String,
    val vendorId: String,
    val rating: Int,
    val title: String,
    val content: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    override var primaryId: String? = null,
): BaseModel