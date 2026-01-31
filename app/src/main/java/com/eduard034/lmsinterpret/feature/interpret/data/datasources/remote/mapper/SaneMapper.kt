package com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.mapper

import com.eduard034.lmsinterpret.feature.interpret.data.datasources.remote.model.SenaDto
import com.eduard034.lmsinterpret.feature.interpret.domain.entities.Sena

fun SenaDto.toDomain(): Sena {
    return Sena(
        id = this.id.toString(),
        nombre = this.nombre,
        // Tu API devuelve URLs relativas o absolutas? Si son relativas agregamos el host aquí si hace falta
        img = this.img,
        descripcion = this.descripcion,
        categoria = this.categoria
    )
}