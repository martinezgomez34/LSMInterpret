package com.eduard034.lmsinterpret.core.navigation

import kotlinx.serialization.Serializable

object Screens {

    @Serializable
    object Interpret

    @Serializable
    data class Detail(val nombreSena: String)

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Profile
}