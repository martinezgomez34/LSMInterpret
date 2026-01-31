package com.eduard034.lmsinterpret.feature.interpret.data.repositories

import com.eduard034.lmsinterpret.core.network.LsmApi
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.mapper.toDomain
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretRequestDto
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository

class InterpretRepositoryImpl(
    private val api: LsmApi
) : InterpretRepository {

    override suspend fun interpretarFrase(texto: String): List<Sena> {
        val request = InterpretRequestDto(texto)
        val response = api.interpretar(request)
        return response.secuencia.map { it.toDomain() }
    }
}