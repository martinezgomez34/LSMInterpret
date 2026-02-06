package com.eduard034.lmsinterpret.feature.login.domain.entities

data class AuthUser(
    val token: String,
    val tokenType: String
)

data class RegisteredUser(
    val id: Int,
    val username: String
)