package com.example.domain

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : UUIDTable("user") {
    val userId = varchar("user_id", 300).uniqueIndex()// 사용자 ID
    val nickname = varchar("username", 50) // 사용자 이름
    val email = varchar("email", 100) // 이메일 주소
    val profileImage = varchar("profile_image", 300) // 프로필 이미지
    val createdAt = datetime("created_at") // 사용자 생성일
}