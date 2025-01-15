package com.example.route

import com.example.dto.BaseDto
import com.example.dto.UserDto
import com.example.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.userRoute() {
    val userService by inject<UserService>()

    routing {
        post("/user") {
            val req = call.receive<UserDto.CreateUserRequest>()
            userService.createUser(req)
            call.respond(
                BaseDto.BaseResponse(
                    success = true,
                    message = "User created"
                )
            )
        }

        get("/user/{userId}") {
            val id = call.parameters["userId"] ?: return@get call.respond(
                BaseDto.BaseResponse(
                    success = false,
                    message = "Invalid id"
                )
            )
            val user = userService.getUserByUserId(id)
            call.respond(user)
        }

        put("/user") {
            val req = call.receive<UserDto.UpdateUserRequest>()
            userService.updateUser(req)
            call.respond(
                BaseDto.BaseResponse(
                    success = true,
                    message = "User updated"
                )
            )
        }

        delete("/user/{primaryKey}") {
            val req = call.parameters["primaryKey"] ?: return@delete call.respond(
                BaseDto.BaseResponse(
                    success = false,
                    message = "Invalid id"
                )
            )
            userService.deleteUser(req)
            call.respond(
                BaseDto.BaseResponse(
                    success = true,
                    message = "User deleted"
                )
            )
        }
    }
}
