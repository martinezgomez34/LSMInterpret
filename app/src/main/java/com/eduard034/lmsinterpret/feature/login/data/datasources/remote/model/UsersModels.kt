package com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val correo: String,
    val password: String
)

@Serializable
data class LoginResponseDto(
    val access_token: String,
    val token_type: String
)

@Serializable
data class RegisterRequestDto(
    val username: String,
    val correo: String,
    val password: String
)

@Serializable
data class RegisterResponseDto(
    val id: Int,
    val username: String,
    val mensaje: String
)