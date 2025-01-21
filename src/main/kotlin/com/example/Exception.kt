package com.example

import com.example.dto.BaseDto
import io.ktor.http.*

class GlobalException(
    val errorCode: ErrorCode,
    val response: BaseDto.BaseResponse,
) : RuntimeException() {

    // 사용자 정의 메시지와 함께 실패 응답 생성
    constructor(errorCode: ErrorCode, errorMessage: String) : this(
        errorCode,
        BaseDto.BaseResponse(
            isSuccess = false,
            message = errorMessage
        )
    )

    constructor(errorCode: ErrorCode) : this(
        errorCode,
        BaseDto.BaseResponse(
            isSuccess = false,
            message = "Bad request"
        )
    )
}

enum class ErrorCode(val httpStatusCode: HttpStatusCode, val defaultMessage: String) {
    USER_NOT_FOUND(HttpStatusCode.Unauthorized, "User not found."),
    USER_ALREADY_EXISTS(HttpStatusCode.BadRequest, "User already exists."),
    FORBIDDEN(HttpStatusCode.Forbidden, "Access denied."),
    BAD_REQUEST(HttpStatusCode.BadRequest, "Bad request.")
}
