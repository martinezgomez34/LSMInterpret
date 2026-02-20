package com.eduard034.lmsinterpret.feature.interpret.domain.usecases

import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import javax.inject.Inject
class InterpretarUseCase @Inject constructor(
    private val repository: InterpretRepository
) {
    suspend operator fun invoke(texto: String): Result<List<Sena>> {
        return try {
            if (texto.isBlank()) {
                return Result.success(emptyList())
            }
            val senas = repository.interpretarFrase(texto)
            Result.success(senas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}