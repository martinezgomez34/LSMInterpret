package com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model

// Lo que enviamos al servidor
data class InterpretRequestDto(
    val texto: String
)

// Lo que recibimos del servidor
data class InterpretResponseDto(
    val original: String,
    val secuencia: List<SenaDto>
)

data class SenaDto(
    val id: Any, // Puede ser int o string según tu API
    val nombre: String,
    val img: List<String>,
    val descripcion: String,
    val categoria: String
)