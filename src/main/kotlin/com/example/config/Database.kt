package com.example.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    val config = environment.config

    val hikariConfig = HikariConfig().apply {
        jdbcUrl = config.property("database.jdbcUrl").getString()
        driverClassName = config.property("database.driverClassName").getString()
        username = config.property("database.user").getString()
        password = config.property("database.password").getString()
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)
}