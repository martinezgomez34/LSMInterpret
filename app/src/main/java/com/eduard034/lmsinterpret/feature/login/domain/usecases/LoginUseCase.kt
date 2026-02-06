package com.eduard034.lmsinterpret.feature.login.domain.usecases

import com.eduard034.lmsinterpret.feature.login.domain.entities.AuthUser
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository

class LoginUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(correo: String, pass: String): Result<AuthUser> {
        // Aquí podrías agregar validaciones de negocio antes de llamar al repo
        // Ej: Validar que el correo tenga formato correcto
        if (correo.isBlank() || pass.isBlank()) {
            return Result.failure(Exception("Campos vacíos"))
        }
        return repository.login(correo, pass)
    }
}