package com.example.route

import com.example.dto.BaseDto
import com.example.dto.ReviewDto
import com.example.dto.UserDto
import com.example.service.ReviewService
import com.example.service.VendorService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.reviewRoute() {
    val reviewService by inject<ReviewService>()

    routing {
        post("/review") {
            val req = call.receive<ReviewDto.CreateReviewRequest>()
            reviewService.createReview(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Review created"
                )
            )
        }

        get("/review/{vendorId}") {
            val id = call.parameters["vendorId"] ?: return@get call.respond(
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Invalid id"
                )
            )
            val review = reviewService.getVendorReviewList(id)
            call.respond(review)
        }

        put("/review") {
            val req = call.receive<ReviewDto.UpdateReviewRequest>()
            reviewService.updateReview(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Review updated"
                )
            )
        }

        delete("/review/{primaryId}") {
            val req = call.parameters["primaryId"] ?: return@delete call.respond(
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Invalid id"
                )
            )
            reviewService.deleteReview(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Review deleted"
                )
            )
        }
    }
}