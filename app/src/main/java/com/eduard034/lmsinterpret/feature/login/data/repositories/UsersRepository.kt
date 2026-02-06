package com.eduard034.lmsinterpret.feature.login.data.repositories

import com.eduard034.lmsinterpret.core.network.UserApi
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.mapper.toDomain
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.LoginRequestDto
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.RegisterRequestDto
import com.eduard034.lmsinterpret.feature.login.domain.entities.AuthUser
import com.eduard034.lmsinterpret.feature.login.domain.entities.RegisteredUser
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun login(correo: String, pass: String): Result<AuthUser> {
        return try {
            val request = LoginRequestDto(correo, pass)
            val response = api.login(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(username: String, correo: String, pass: String): Result<RegisteredUser> {
        return try {
            val request = RegisterRequestDto(username, correo, pass)
            val response = api.register(request)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}