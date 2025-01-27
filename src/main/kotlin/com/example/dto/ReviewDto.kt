package com.example.dto

import com.example.domain.model.Review
import kotlinx.serialization.Serializable

class ReviewDto {
    @Serializable
    data class CreateReviewRequest (
        val writerId: String,
        val vendorId: String,
        val rating: Int,
        val title: String,
        val content: String,
    )

    @Serializable
    data class GetVendorReviewListResponse (
        val isSuccess: Boolean,
        val message: String,
        val reviewList: List<Review>
    )

    @Serializable
    data class GetReviewResponse (
        val isSuccess: Boolean,
        val message: String,
        val review: Review
    )

    @Serializable
    data class UpdateReviewRequest (
        val writerId: String,
        val vendorId: String,
        val rating: Int,
        val title: String,
        val content: String,
        val primaryId: String,
    )
}