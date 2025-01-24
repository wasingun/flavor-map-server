package com.example.config

import com.example.domain.UserTable
import com.example.domain.VendorLocationTable
import com.example.domain.VendorTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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

    transaction {
        SchemaUtils.create(
            UserTable,
            VendorTable,
            VendorLocationTable
        )
    }
}