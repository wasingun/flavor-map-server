package com.example.domain.model

import com.example.LocalDateTimeSerializer
import com.example.domain.BaseModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
class VendorLocation(
    val vendorId: String,
    val currentLatitude: Double,
    val currentLongitude: Double,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Contextual override var primaryId: String? = null,
): BaseModel
