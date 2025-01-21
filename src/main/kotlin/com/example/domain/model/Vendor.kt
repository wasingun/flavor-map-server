package com.example.domain.model

import com.example.LocalDateTimeSerializer
import com.example.domain.BaseModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Vendor(
    val vendorId: String,
    val email: String,
    val vendorName: String,
    val photoUrl: String,
    val operatingHours: String,
    val contactNumber: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Contextual override var primaryId: UUID? = null,
) : BaseModel