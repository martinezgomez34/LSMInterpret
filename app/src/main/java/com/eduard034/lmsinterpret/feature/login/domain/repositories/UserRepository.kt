package com.eduard034.lmsinterpret.feature.login.domain.repositories

import com.eduard034.lmsinterpret.feature.login.domain.entities.AuthUser
import com.eduard034.lmsinterpret.feature.login.domain.entities.RegisteredUser

interface UserRepository {
    suspend fun login(correo: String, pass: String): Result<AuthUser>
    suspend fun register(username: String, correo: String, pass: String): Result<RegisteredUser>
}