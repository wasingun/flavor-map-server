package com.example.config

import com.example.GlobalException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<GlobalException> { call, cause ->
            call.respond(cause.errorCode.httpStatusCode, cause.response)
        }
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Unexpected error occurred."
                )
            )
        }
    }
}

class BaseDto {
    @Serializable
    data class BaseResponse(
        val isSuccess: Boolean,
        val message: String
    )
}