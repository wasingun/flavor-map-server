package com.example.service

import com.example.ErrorCode
import com.example.GlobalException
import com.example.domain.model.Review
import com.example.domain.repository.ReviewRepository
import com.example.dto.ReviewDto
import java.time.LocalDateTime
import java.util.*

class ReviewService (
    val reviewRepository: ReviewRepository
) {
    fun createReview(review: ReviewDto.CreateReviewRequest) {
        val existReview =
            reviewRepository.findByVendorId(review.vendorId).firstOrNull { it.writerId == review.writerId }
        if (existReview != null) {
            throw GlobalException(
                errorCode = ErrorCode.USER_ALREADY_EXISTS,
                errorMessage = "리뷰는 하나만 작성 가능합니다."
            )
        }
        reviewRepository.create(
            Review(
                writerId = review.writerId,
                vendorId = review.vendorId,
                rating = review.rating,
                title = review.title,
                content = review.content,
                createdAt = LocalDateTime.now()
            )
        )
    }

    fun getVendorReviewList(vendorId : String): ReviewDto.GetVendorReviewListResponse {
        val reviewList = reviewRepository.findByVendorId(vendorId)
        return ReviewDto.GetVendorReviewListResponse(
            isSuccess = true,
            message = "Request Success",
            reviewList = reviewList
        )
    }

    fun updateReview(review: ReviewDto.UpdateReviewRequest) {
        val existReview = reviewRepository.read(UUID.fromString(review.primaryId))
        if (existReview == null) {
            throw GlobalException(
                errorCode = ErrorCode.REVIEW_NOT_FOUND,
                errorMessage = ErrorCode.REVIEW_NOT_FOUND.defaultMessage
            )
        } else if (existReview.vendorId != review.vendorId) {
            throw GlobalException(
                errorCode = ErrorCode.DATA_INTEGRITY_VIOLATION,
                errorMessage = ErrorCode.DATA_INTEGRITY_VIOLATION.defaultMessage
            )
        }
        reviewRepository.update(
            Review(
                writerId = review.writerId,
                vendorId = review.vendorId,
                rating = review.rating,
                title = review.title,
                content = review.content,
                createdAt = existReview.createdAt,
                primaryId = review.primaryId
            )
        )
    }

    fun deleteReview(primaryKey: String) {
        val primaryKeyUuid = UUID.fromString(primaryKey)
        val existedPrimaryKey = reviewRepository.read(primaryKeyUuid)
        if (existedPrimaryKey == null) {
            throw GlobalException(
                errorCode = ErrorCode.REVIEW_NOT_FOUND,
                errorMessage = ErrorCode.REVIEW_NOT_FOUND.defaultMessage
            )
        }
        reviewRepository.delete(primaryKeyUuid)
    }
}