package com.eduard034.lmsinterpret.feature.profile.data.remote

import com.google.gson.annotations.SerializedName

// Respuesta del perfil
data class UserProfileDto(
    val id: Int,
    val username: String,
    val correo: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String
)

// Request para actualizar username
data class UpdateProfileRequest(
    val username: String
)

// Request para actualizar password
data class UpdatePasswordRequest(
    val newPassword: String
)