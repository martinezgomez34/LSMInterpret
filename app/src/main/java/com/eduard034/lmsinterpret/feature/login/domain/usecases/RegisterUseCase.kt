package com.eduard034.lmsinterpret.feature.login.domain.usecases

import com.eduard034.lmsinterpret.feature.login.domain.entities.RegisteredUser
import com.eduard034.lmsinterpret.feature.login.domain.repositories.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String, correo: String, pass: String): Result<RegisteredUser> {
        return repository.register(username, correo, pass)
    }
}