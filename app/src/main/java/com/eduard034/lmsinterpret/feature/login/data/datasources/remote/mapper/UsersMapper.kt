package com.eduard034.lmsinterpret.feature.login.data.datasources.remote.mapper

import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.LoginResponseDto
import com.eduard034.lmsinterpret.feature.login.data.datasources.remote.model.RegisterResponseDto
import com.eduard034.lmsinterpret.feature.login.domain.entities.AuthUser
import com.eduard034.lmsinterpret.feature.login.domain.entities.RegisteredUser

fun LoginResponseDto.toDomain(): AuthUser {
    return AuthUser(
        token = this.access_token,
        tokenType = this.token_type
    )
}

fun RegisterResponseDto.toDomain(): RegisteredUser {
    return RegisteredUser(
        id = this.id,
        username = this.username
    )
}