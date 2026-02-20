package com.eduard034.lmsinterpret.feature.login.domain.usecases

import com.eduard034.lmsinterpret.feature.login.domain.entities.AuthUser
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(correo: String, pass: String): Result<AuthUser> {
        if (correo.isBlank() || pass.isBlank()) {
            return Result.failure(Exception("Campos vacíos"))
        }
        return repository.login(correo, pass)
    }
}