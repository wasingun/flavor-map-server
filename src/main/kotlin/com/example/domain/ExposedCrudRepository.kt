package com.example.domain

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*


interface ExposedCrudRepository<TABLE : UUIDTable, DOMAIN : BaseModel> : CrudRepository<DOMAIN> {
    val table: TABLE
    override fun create(domain: DOMAIN): DOMAIN = dbQuery {
        val id = table.insertAndGetId(toRow(domain))
        domain.primaryId = id.value
        domain
    }

    override fun findAll(): List<DOMAIN> = dbQuery {
        table.selectAll().map { toDomain(it) }
    }

    override fun read(id: UUID): DOMAIN? = dbQuery {
        table.selectAll().where { table.id eq id }
            .map { toDomain(it) }
            .singleOrNull()
    }

    override fun update(domain: DOMAIN): DOMAIN = dbQuery {
        table.update(
            where = { table.id eq domain.primaryId },
            body = updateRow(domain)
        )
        domain
    }

    override fun delete(domain: DOMAIN) = dbQuery {
        table.deleteWhere { table.id eq domain.primaryId }
        return@dbQuery
    }

    override fun delete(id: UUID) = dbQuery {
        table.deleteWhere { table.id eq id }
        return@dbQuery
    }

    fun toRow(domain: DOMAIN): TABLE.(InsertStatement<EntityID<UUID>>) -> Unit
    fun toDomain(row: ResultRow): DOMAIN
    fun updateRow(domain: DOMAIN): TABLE.(UpdateStatement) -> Unit

    fun <T> dbQuery(block: () -> T): T =
        transaction {
            addLogger(StdOutSqlLogger)
            block()
    }
}
