package com.example.dto

import com.example.LocalDateTimeSerializer
import com.example.domain.model.VendorLocation
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

class VendorLocationDto {
    @Serializable
    data class CreateVendorLocationRequest(
        val vendorId: String,
        val currentLatitude: Double,
        val currentLongitude: Double,
    )

    @Serializable
    data class GetAllVendorLocationResponse(
        val isSuccess: Boolean,
        val message: String,
        val vendorLocationList: List<VendorLocation>
    )

    @Serializable
    data class UpdateVendorLocationRequest(
        val vendorId: String,
        val currentLatitude: Double,
        val currentLongitude: Double,
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime,
        val primaryId: String,
    )
}
