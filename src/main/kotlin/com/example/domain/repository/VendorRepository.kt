package com.example.domain.repository

import com.example.domain.ExposedCrudRepository
import com.example.domain.VendorTable
import com.example.domain.model.Vendor
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.util.*

class VendorRepository(
    override val table: VendorTable
) : ExposedCrudRepository<VendorTable, Vendor> {
    override fun toRow(domain: Vendor): VendorTable.(InsertStatement<EntityID<UUID>>) -> Unit {
        return {
            if (domain.primaryId != null) {
                it[id] = UUID.fromString(domain.primaryId!!)
            }
            it[vendorId] = domain.vendorId
            it[email] = domain.email
            it[vendorName] = domain.vendorName
            it[photoUrl] = domain.photoUrl
            it[operatingHours] = domain.operatingHours
            it[contactNumber] = domain.contactNumber
            it[createAt] = domain.createdAt
        }
    }

    override fun toDomain(row: ResultRow): Vendor {
        return Vendor(
            vendorId = row[VendorTable.vendorId],
            email = row[VendorTable.email],
            vendorName = row[VendorTable.vendorName],
            photoUrl = row[VendorTable.photoUrl],
            operatingHours = row[VendorTable.operatingHours],
            contactNumber = row[VendorTable.contactNumber],
            createdAt = row[VendorTable.createAt],
            primaryId = row[VendorTable.id].value.toString()
        )
    }

    override fun updateRow(domain: Vendor): VendorTable.(UpdateStatement) -> Unit {
        return {
            it[vendorId] = domain.vendorId
            it[email] = domain.email
            it[vendorName] = domain.vendorName
            it[photoUrl] = domain.photoUrl
            it[operatingHours] = domain.operatingHours
            it[contactNumber] = domain.contactNumber
            it[createAt] = domain.createdAt
        }
    }

    fun findByVendorId(vendorId: String): Vendor? {
        return dbQuery {
            val result = table.selectAll().where { table.vendorId.eq(vendorId) }
            result.map(::toDomain).firstOrNull()
        }
    }
}