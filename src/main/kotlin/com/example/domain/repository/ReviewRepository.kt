package com.example.domain.repository

import com.example.domain.ExposedCrudRepository
import com.example.domain.ReviewTable
import com.example.domain.model.Review
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.util.*

class ReviewRepository(
    override val table: ReviewTable
) : ExposedCrudRepository<ReviewTable, Review> {
    override fun toRow(domain: Review): ReviewTable.(InsertStatement<EntityID<UUID>>) -> Unit {
        return {
            if (domain.primaryId != null) {
                it[id] = UUID.fromString(domain.primaryId!!)
            }
            it[writerId] = domain.writerId
            it[vendorId] = domain.vendorId
            it[rating] = domain.rating
            it[title] = domain.title
            it[content] = domain.content
            it[createdAt] = domain.createdAt
        }
    }

    override fun toDomain(row: ResultRow): Review {
        return Review(
            writerId = row[ReviewTable.writerId],
            vendorId = row[ReviewTable.vendorId],
            rating = row[ReviewTable.rating],
            title = row[ReviewTable.title],
            content = row[ReviewTable.content],
            createdAt = row[ReviewTable.createdAt],
            primaryId = row[ReviewTable.id].value.toString()
        )
    }

    override fun updateRow(domain: Review): ReviewTable.(UpdateStatement) -> Unit {
        return {
            it[writerId] = domain.writerId
            it[vendorId] = domain.vendorId
            it[rating] = domain.rating
            it[title] = domain.title
            it[content] = domain.content
            it[createdAt] = domain.createdAt
        }
    }

    fun findByVendorId(vendorId: String): List<Review> {
        return dbQuery {
            val result = table.selectAll().where { table.vendorId.eq(vendorId) }
            result.map(::toDomain)
        }
    }

    fun findByWriterId(writerId: String): List<Review> {
        return dbQuery {
            val result = table.selectAll().where { table.writerId.eq(writerId) }
            result.map(::toDomain)
        }
    }
}