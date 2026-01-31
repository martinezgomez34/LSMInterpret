package com.eduard034.lmsinterpret.core.network

import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretRequestDto
import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.InterpretResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LsmApi {
    @POST("api/interpretar")
    suspend fun interpretar(@Body request: InterpretRequestDto): InterpretResponseDto
}