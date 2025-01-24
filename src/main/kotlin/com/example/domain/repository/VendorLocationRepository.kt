package com.example.domain.repository

import com.example.domain.ExposedCrudRepository
import com.example.domain.VendorLocationTable
import com.example.domain.model.VendorLocation
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.util.*

class VendorLocationRepository(
    override val table: VendorLocationTable
) : ExposedCrudRepository<VendorLocationTable, VendorLocation> {
    override fun toRow(domain: VendorLocation): VendorLocationTable.(InsertStatement<EntityID<UUID>>) -> Unit {
        return {
            if (domain.primaryId != null) {
                it[id] = domain.primaryId!!
            }
            it[vendorId] = domain.vendorId
            it[currentLatitude] = domain.currentLatitude
            it[currentLongitude] = domain.currentLongitude
            it[createdAt] = domain.createdAt
        }
    }

    override fun toDomain(row: ResultRow): VendorLocation {
        return VendorLocation(
            vendorId = row[VendorLocationTable.vendorId],
            currentLatitude = row[VendorLocationTable.currentLatitude],
            currentLongitude = row[VendorLocationTable.currentLongitude],
            createdAt = row[VendorLocationTable.createdAt],
            primaryId = row[VendorLocationTable.id].value
        )
    }

    override fun updateRow(domain: VendorLocation): VendorLocationTable.(UpdateStatement) -> Unit {
        return {
            it[vendorId] = domain.vendorId
            it[currentLatitude] = domain.currentLatitude
            it[currentLongitude] = domain.currentLongitude
            it[createdAt] = domain.createdAt
        }
    }

    fun findByVendorId(vendorId: String): VendorLocation? {
        return dbQuery {
            val result = table.selectAll().where { table.vendorId.eq(vendorId) }
            result.map(::toDomain).firstOrNull()
        }
    }
}