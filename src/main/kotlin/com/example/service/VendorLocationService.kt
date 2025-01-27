package com.example.service

import com.example.ErrorCode
import com.example.GlobalException
import com.example.domain.model.VendorLocation
import com.example.domain.repository.VendorLocationRepository
import com.example.dto.VendorLocationDto
import java.time.LocalDateTime
import java.util.UUID

class VendorLocationService(
    private val vendorLocationRepository: VendorLocationRepository
) {
    fun createVendorLocation(vendorLocation: VendorLocationDto.CreateVendorLocationRequest) {
        val existedVendorLocation = vendorLocationRepository.findByVendorId(vendorLocation.vendorId)
        if (existedVendorLocation != null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_ALREADY_EXISTS,
                errorMessage = "Vendor location already exists"
            )
        }
        vendorLocationRepository.create(
            VendorLocation(
                vendorId = vendorLocation.vendorId,
                currentLatitude = vendorLocation.currentLatitude,
                currentLongitude = vendorLocation.currentLongitude,
                createdAt = LocalDateTime.now()
            )
        )
    }

    fun getAllVendorLocationByVendorId(): VendorLocationDto.GetAllVendorLocationResponse {
        val existedVendorLocation = vendorLocationRepository.findAll()
        return VendorLocationDto.GetAllVendorLocationResponse(
            isSuccess = true,
            message = "Request Success",
            data = existedVendorLocation
        )
    }

    fun updateVendorLocation(vendorLocation: VendorLocationDto.UpdateVendorLocationRequest) {
        val existedVendorLocation = vendorLocationRepository.findByVendorId(vendorLocation.vendorId)
        if (existedVendorLocation == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        } else if (existedVendorLocation.primaryId != vendorLocation.primaryId) {
            throw GlobalException(
                errorCode = ErrorCode.DATA_INTEGRITY_VIOLATION,
                errorMessage = ErrorCode.DATA_INTEGRITY_VIOLATION.defaultMessage
            )
        }
        vendorLocationRepository.update(
            VendorLocation(
                vendorId = vendorLocation.vendorId,
                currentLatitude = vendorLocation.currentLatitude,
                currentLongitude = vendorLocation.currentLongitude,
                createdAt = vendorLocation.createdAt,
                primaryId = vendorLocation.primaryId
            )
        )
    }

    fun deleteVendorLocation(primaryKey: String) {
        val uuidPrimaryKey = UUID.fromString(primaryKey)
        val existedVendorLocation = vendorLocationRepository.read(uuidPrimaryKey)
        if (existedVendorLocation == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        }
        vendorLocationRepository.delete(uuidPrimaryKey)
    }
}