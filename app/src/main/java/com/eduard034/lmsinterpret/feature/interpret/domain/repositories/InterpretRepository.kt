package com.eduard034.lmsinterpret.feature.interpret.domain.repositories

import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena

interface InterpretRepository {
    suspend fun interpretarFrase(texto: String): List<Sena>
}