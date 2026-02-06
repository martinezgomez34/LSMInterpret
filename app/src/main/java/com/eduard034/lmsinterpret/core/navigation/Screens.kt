package com.eduard034.lmsinterpret.core.navigation

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    object Interpret // La pantalla principal

    @Serializable
    data class Detail(val nombreSena: String) // La pantalla de detalle
}