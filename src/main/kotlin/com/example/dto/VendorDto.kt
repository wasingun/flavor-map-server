package com.example.dto

import com.example.LocalDateTimeSerializer
import com.example.domain.model.Vendor
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

class VendorDto {
    @Serializable
    data class CreateVendorRequest(
        val vendorId: String,
        val email: String,
        val vendorName: String,
        val photoUrl: String,
        val operatingHours: String,
        val contactNumber: String
    )

    @Serializable
    data class GetVendorResponse (
        val isSuccess: Boolean,
        val message: String,
        val data: Vendor
    )

    @Serializable
    data class GetAllVendorResponse (
        val isSuccess: Boolean,
        val message: String,
        val data: List<Vendor>
    )

    @Serializable
    data class UpdateVendorRequest(
        val vendorId: String,
        val vendorName: String,
        val photoUrl: String,
        val operatingHours: String,
        val contactNumber: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime,
        @Contextual val primaryId: UUID,
    )
}