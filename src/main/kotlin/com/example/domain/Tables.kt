package com.example.domain

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : UUIDTable("user") {
    val userId = varchar("user_id", 300).uniqueIndex()// 사용자 ID
    val nickname = varchar("username", 50) // 사용자 이름
    val email = varchar("email", 100) // 이메일 주소
    val profileImage = varchar("profile_image", 300) // 프로필 이미지
    val createdAt = datetime("created_at") // 사용자 생성일
}

object VendorTable : UUIDTable("vendor") {
    val vendorId = varchar("vendor_id", 300).uniqueIndex() // 업체 ID
    val email = varchar("email", 100) // 이메일 주소
    val vendorName = varchar("shop_name", 100) // 상호명
    val photoUrl = varchar("photo_url", 300) // 사진 URL
    val operatingHours = varchar("operating_hours", 100) // 영업 시간
    val contactNumber = varchar("contact_number", 20) // 연락처
    val createAt = datetime("created_at") // 업체 생성일
}

object VendorLocationTable : UUIDTable("vendor_location") {
    val vendorId = varchar("vendor_id", 300).uniqueIndex() // 업체 ID
    val currentLatitude = double("current_latitude") // 현재 위도
    val currentLongitude = double("current_longitude") // 현재 경도
    val createdAt = datetime("created_at") // 생성일
}