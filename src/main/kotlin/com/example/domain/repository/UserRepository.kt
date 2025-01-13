package com.example.domain.repository

import com.example.domain.ExposedCrudRepository
import com.example.domain.UserTable
import com.example.domain.model.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

class UserRepository(
    override val table: UserTable
) : ExposedCrudRepository<UserTable, User> {
    override fun toRow(domain: User): UserTable.(InsertStatement<EntityID<Long>>) -> Unit {
        return {
            if (domain.primaryId != null) {
                it[id] = domain.primaryId!!
            }
            it[userId] = domain.userId
            it[nickname] = domain.nickname
            it[email] = domain.email
            it[profileImage] = domain.profileImage
            it[createdAt] = domain.createdAt
        }
    }

    override fun toDomain(row: ResultRow): User {
        return User(
            userId = row[UserTable.userId],
            nickname = row[UserTable.nickname],
            email = row[UserTable.email],
            profileImage = row[UserTable.profileImage],
            createdAt = row[UserTable.createdAt],
            primaryId = row[UserTable.id].value
        )
    }

    override fun updateRow(domain: User): UserTable.(UpdateStatement) -> Unit {
        return {
            it[userId] = domain.userId
            it[nickname] = domain.nickname
            it[email] = domain.email
            it[profileImage] = domain.profileImage
            it[createdAt] = domain.createdAt
        }
    }

    fun findByUserId(userId: String): User? {
        return dbQuery {
            val result = table.selectAll().where { table.userId.eq(userId) }
            result.map(::toDomain).firstOrNull()
        }
    }
}