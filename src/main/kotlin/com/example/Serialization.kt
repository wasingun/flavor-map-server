package com.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

fun Application.configureSerialization() {
/*    val uuidSerializersModule = SerializersModule {
        contextual(UUIDSerializer) // UUIDSerializer 등록
    }*/
    install(ContentNegotiation) {
/*        json(
            Json {
                serializersModule = uuidSerializersModule
            }
        )*/
        json()
    }
    routing {
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
