package com.example

import com.example.config.configureDatabase
import com.example.config.configureDependencyInjection
import com.example.config.configureErrorHandling
import com.example.route.userRoute
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabase()
    configureSerialization()
    configureDependencyInjection()
    configureErrorHandling()
    userRoute()
}
