package com.example.route

import com.example.dto.BaseDto
import com.example.dto.VendorLocationDto
import com.example.service.VendorLocationService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.vendorLocationRoute() {
    val vendorLocationService by inject<VendorLocationService>()

    routing {
        post("/vendorLocation") {
            val req = call.receive<VendorLocationDto.CreateVendorLocationRequest>()
            vendorLocationService.createVendorLocation(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Vendor location created"
                )
            )
        }

        get("/vendorLocation/all") {
            val vendorLocationList = vendorLocationService.getAllVendorLocationByVendorId()
            call.respond(vendorLocationList)
        }

        put("/vendorLocation") {
            val req = call.receive<VendorLocationDto.UpdateVendorLocationRequest>()
            vendorLocationService.updateVendorLocation(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Vendor location updated"
                )
            )
        }

        delete("/vendorLocation/{primaryId}") {
            val req = call.parameters["primaryId"] ?: return@delete call.respond(
                BaseDto.BaseResponse(
                    isSuccess = false,
                    message = "Vendor location information not found"
                )
            )
            vendorLocationService.deleteVendorLocation(req)
            call.respond(
                BaseDto.BaseResponse(
                    isSuccess = true,
                    message = "Vendor location deleted"
                )
            )
        }
    }
}