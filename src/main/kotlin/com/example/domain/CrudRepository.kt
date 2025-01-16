package com.example.domain

import java.util.*

interface CrudRepository<DOMAIN : BaseModel> {
    fun create(domain: DOMAIN): DOMAIN
    fun findAll(): List<DOMAIN>
    fun read(id: UUID): DOMAIN?
    fun update(domain: DOMAIN): DOMAIN
    fun delete(domain: DOMAIN)
    fun delete(id: UUID)
}
