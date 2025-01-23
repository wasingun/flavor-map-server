package com.example.service

import com.example.ErrorCode
import com.example.GlobalException
import com.example.domain.model.Vendor
import com.example.domain.repository.VendorRepository
import com.example.dto.VendorDto
import java.time.LocalDateTime
import java.util.*

class VendorService(private val vendorRepository: VendorRepository) {
    fun createVendor(createVendorRequest: VendorDto.CreateVendorRequest) {
        val existVendor = vendorRepository.findByVendorId(createVendorRequest.vendorId)
        if (existVendor != null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_ALREADY_EXISTS,
                errorMessage = ErrorCode.USER_ALREADY_EXISTS.defaultMessage
            )
        }
        vendorRepository.create(
            Vendor(
                vendorId = createVendorRequest.vendorId,
                email = createVendorRequest.email,
                vendorName = createVendorRequest.vendorName,
                photoUrl = createVendorRequest.photoUrl,
                operatingHours = createVendorRequest.operatingHours,
                contactNumber = createVendorRequest.contactNumber,
                createdAt = LocalDateTime.now(),
            )
        )

    }

    fun getVendorById(vendorId: String): VendorDto.GetVendorResponse {
        val existVendor = vendorRepository.findByVendorId(vendorId) ?: throw GlobalException(
            errorCode = ErrorCode.USER_NOT_FOUND,
            errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
        )

        return VendorDto.GetVendorResponse(
            isSuccess = true,
            message = "Request Success",
            data = Vendor(
                vendorId = existVendor.vendorId,
                email = existVendor.email,
                vendorName = existVendor.vendorName,
                photoUrl = existVendor.photoUrl,
                operatingHours = existVendor.operatingHours,
                contactNumber = existVendor.contactNumber,
                createdAt = existVendor.createdAt,
                primaryId = existVendor.primaryId!!
            )
        )
    }

    fun updateVendor(vendor: VendorDto.UpdateVendorRequest) {
        val existVendor = vendorRepository.findByVendorId(vendor.vendorId)
        if (existVendor == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        } else if (existVendor.primaryId != vendor.primaryId) {
            throw GlobalException(
                errorCode = ErrorCode.DATA_INTEGRITY_VIOLATION,
                errorMessage = ErrorCode.DATA_INTEGRITY_VIOLATION.defaultMessage
            )
        }

        vendorRepository.update(
            Vendor(
                vendorId = vendor.vendorId,
                email = existVendor.email,
                vendorName = vendor.vendorName,
                photoUrl = vendor.photoUrl,
                operatingHours = vendor.operatingHours,
                contactNumber = vendor.contactNumber,
                createdAt = vendor.createdAt,
                primaryId = vendor.primaryId
            )
        )
    }

    fun deleteVendor(primaryKey: String) {
        val primaryKeyUuid = UUID.fromString(primaryKey)
        val existedPrimaryKey = vendorRepository.read(primaryKeyUuid)
        if (existedPrimaryKey == null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        }
        vendorRepository.delete(primaryKeyUuid)
    }

    fun getAllVendors(): VendorDto.GetAllVendorResponse {
        val vendorList = vendorRepository.findAll()
        if (vendorList.isEmpty()) {
            throw GlobalException(
                errorCode = ErrorCode.USER_NOT_FOUND,
                errorMessage = ErrorCode.USER_NOT_FOUND.defaultMessage
            )
        }

        return VendorDto.GetAllVendorResponse(
            isSuccess = true,
            message = "Request Success",
            data = vendorList
        )
    }
}