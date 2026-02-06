package com.eduard034.lmsinterpret.core.network

import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretRequestDto
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretResponseDto
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.SenaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LsmApi {
    @POST("api/interpretar")
    suspend fun interpretar(@Body request: InterpretRequestDto): InterpretResponseDto

    @GET("api/sena/{nombre}")
    suspend fun getSena(@Path("nombre") nombre: String): SenaDto
}