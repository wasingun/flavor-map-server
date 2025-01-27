package com.example.config

import com.example.domain.UserTable
import com.example.domain.VendorLocationTable
import com.example.domain.VendorTable
import com.example.domain.repository.UserRepository
import com.example.domain.repository.VendorLocationRepository
import com.example.domain.repository.VendorRepository
import com.example.service.UserService
import com.example.service.VendorLocationService
import com.example.service.VendorService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val appModule = module {
    single { UserRepository(UserTable) }
    single { UserService(get()) }
    single { VendorRepository(VendorTable) }
    single { VendorService(get()) }
    single { VendorLocationRepository(VendorLocationTable) }
    single { VendorLocationService(get()) }
}

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
