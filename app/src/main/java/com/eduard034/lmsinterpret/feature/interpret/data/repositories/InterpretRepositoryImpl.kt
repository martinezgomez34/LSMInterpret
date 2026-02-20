package com.eduard034.lmsinterpret.feature.interpret.data.repositories

import com.eduard034.lmsinterpret.core.network.LsmApi
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.mapper.toDomain
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretRequestDto
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena
import com.eduard034.lmsinterpret.feature.interpret.domain.repositories.InterpretRepository
import javax.inject.Inject
class InterpretRepositoryImpl @Inject constructor(
    private val api: LsmApi
) : InterpretRepository {

    override suspend fun interpretarFrase(texto: String): List<Sena> {
        return try {
            val response = api.interpretar(InterpretRequestDto(texto = texto))
            response.secuencia.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getSenaByName(nombre: String): Sena? {
        return try {
            val senaDto = api.getSena(nombre)

            senaDto.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}