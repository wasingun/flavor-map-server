package com.example.config

import com.example.domain.UserTable
import com.example.domain.repository.UserRepository
import com.example.service.UserService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val appModule = module {
    single { UserRepository(UserTable) }
    single { UserService(get()) }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
