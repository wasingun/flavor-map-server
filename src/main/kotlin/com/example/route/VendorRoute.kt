package com.example.route

import com.example.dto.BaseDto
import com.example.dto.VendorDto
import com.example.service.VendorService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.vendorRoute() {
    val vendorService by inject<VendorService>()

    routing {
        post("/vendor") {
            val req = call.receive<VendorDto.CreateVendorRequest>()
            vendorService.createVendor(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "User created"
                )
            )
        }

        get("/vendor/{vendorId}") {
            val id = call.parameters["vendorId"] ?: return@get call.respond(
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Invalid id"
                )
            )
            val vendor = vendorService.getVendorById(id)
            call.respond(vendor)
        }

        put("/vendor") {
            val req = call.receive<VendorDto.UpdateVendorRequest>()
            vendorService.updateVendor(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "User updated"
                )
            )
        }

        delete ("/vendor/{primaryId}") {
            val req = call.parameters["primaryId"] ?: return@delete call.respond(
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Invalid id"
                )
            )
            vendorService.deleteVendor(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "User deleted"
                )
            )
        }

        get ("/vendor/all") {
            val vendors = vendorService.getAllVendors()
            call.respond(vendors)
        }
    }
}